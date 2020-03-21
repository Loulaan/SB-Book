package com.hronosf.security.jwt.service.impl;

import com.hronosf.exception.specified.JwtServiceException;
import com.hronosf.dto.responses.LoginResponseDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import com.hronosf.security.jwt.service.JwtTokenService;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Setter
@Service
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {

    private String jwtHeader;
    private String secretWordToken;
    private String secretWordRefreshToken;
    private long tokenExpirationPeriodSec;
    private long refreshTokenExpirationPeriodSec;

    private final UserDetailsService userDetailsService;

    @PostConstruct
    public void encodeSecrets() {
        secretWordToken = Base64.getEncoder().encodeToString(secretWordToken.getBytes());
        secretWordRefreshToken = Base64.getEncoder().encodeToString(secretWordRefreshToken.getBytes());
    }

    @Override
    public LoginResponseDTO generateJwtTokens(String userName) {
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", jwtTokensGenerator(userName, false));
        tokenMap.put("refreshToken", jwtTokensGenerator(userName, true));
        return new LoginResponseDTO(tokenMap);
    }

    @Override
    public LoginResponseDTO refreshJwtTokens(String refreshToken, String token) {
        try {
            isTokenValid(token);
        } catch (ExpiredJwtException exception) {
            isTokenValid(refreshToken, true);
            return generateJwtTokens(extractUserNameFromToken(refreshToken, true));
        }
        throw new JwtServiceException("api-errors.tokenIsValid");
    }

    @Override
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(extractUserNameFromToken(token, false));
        return new UsernamePasswordAuthenticationToken(userDetails, StringUtils.EMPTY, userDetails.getAuthorities());
    }

    @Override
    public String extractJwtToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith(jwtHeader)) {
            return header.replace(jwtHeader, StringUtils.EMPTY);
        }
        return null;
    }

    @Override
    public boolean isTokenValid(String token) {
        return isTokenValid(token, false);
    }

    @Override
    public String extractUserNameFromJwtToken(HttpServletRequest request) {
        String token = extractJwtToken(request);
        return extractUserNameFromToken(token, false);
    }

    private boolean isTokenValid(String token, boolean isRefresh) {
        String secret = isRefresh ? secretWordRefreshToken : secretWordToken;
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        return claimsJws.getBody().getExpiration().after(new Date());
    }

    private String jwtTokensGenerator(String userName, boolean isRefresh) {
        Claims claims = Jwts.claims().setSubject(userName);
        claims.put("roles", authoritiesToRoles(userDetailsService.loadUserByUsername(userName).getAuthorities()));

        Date now = new Date();
        long expirationPeriod = isRefresh ? refreshTokenExpirationPeriodSec : tokenExpirationPeriodSec;
        String secret = isRefresh ? secretWordRefreshToken : secretWordToken;
        Date expireTime = new Date(now.getTime() + expirationPeriod);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expireTime)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    private List<String> authoritiesToRoles(Collection<? extends GrantedAuthority> userAuthorities) {
        return userAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }

    private String extractUserNameFromToken(String token, boolean isRefresh) {
        String secret = isRefresh ? secretWordRefreshToken : secretWordToken;
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }
}
