package security.jwt.service.implementation;

import exception.ExceptionFactoryHelper;
import security.jwt.exception.JwtAuthenticationException;
import security.jwt.service.JwtTokenService;
import dto.AuthTokensDTO;
import io.jsonwebtoken.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormat;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Data
@Component
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {

    private String secretWord;
    private Long expireTimeMs;
    private String jwtHeaderPrefix;
    private Long expireTimeMsRefresh;
    private String secretWordRefresh;
    private final ExceptionFactoryHelper messageSource;
    private final UserDetailsService userDetailsService;

    private static final Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\+\\d{4}");

    @PostConstruct
    private void init() {
        secretWord = Base64.getEncoder().encodeToString(secretWord.getBytes());
    }

    @Override
    public AuthTokensDTO createAuthTokens(String username) {
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", generateToken(username, false));
        tokenMap.put("refreshToken", generateToken(username, true));
        return new AuthTokensDTO(tokenMap);
    }

    @Override
    public AuthTokensDTO refreshAuthTokens(String refreshToken, String token) {
        try {
            isTokenValid(token);
        } catch (JwtAuthenticationException ex) {
            if (isRefreshTokenValid(refreshToken)) {
                String username = getUserNameByToken(refreshToken, true);
                return createAuthTokens(username);
            }
        }
        throw new JwtAuthenticationException(messageSource.buildMessage("errors.token.still.valid"));
    }

    @Override
    public Authentication getAuthentication(String token) {
        isTokenValid(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUserNameByToken(token, false));
        return new UsernamePasswordAuthenticationToken(userDetails, StringUtils.EMPTY, userDetails.getAuthorities());
    }

    @Override
    public boolean isTokenValid(String token) {
        return validateToken(token, false);
    }

    @Override
    public String handleToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith(jwtHeaderPrefix)) {
            return header.replace(jwtHeaderPrefix, StringUtils.EMPTY);
        }
        return null;
    }

    @Override
    public UserDetails getUserDetailsByToken(HttpServletRequest request) {
        try {
            String token = handleToken(request);
            isTokenValid(token);
            return userDetailsService.loadUserByUsername(getUserNameByToken(token, false));
        } catch (IllegalArgumentException exception) {
            throw new AccessDeniedException(messageSource.buildMessage("errors.token.not.found"));
        }
    }

    private String getUserNameByToken(String token, boolean isRefresh) {
        String secret = isRefresh ? secretWordRefresh : secretWord;
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    private boolean isRefreshTokenValid(String token) {
        return validateToken(token, true);
    }

    private String generateToken(String username, boolean isRefresh) {
        return isRefresh ?
                jwtGenerator(username, expireTimeMsRefresh, secretWordRefresh) : jwtGenerator(username, expireTimeMs, secretWord);
    }

    @SneakyThrows
    private boolean validateToken(String token, boolean isRefresh) {
        try {
            String secret = isRefresh ? secretWordRefresh : secretWord;
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return claims.getBody().getExpiration().after(new Date());
        } catch (ExpiredJwtException exception) {
            Matcher matcher = pattern.matcher(exception.getMessage());
            Date expiredAt = null;
            if (matcher.find(1)) {
                expiredAt = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").parse(matcher.group(0).replace("T", " "));
            }
            Date now = new Date();
            Period period = new Period(expiredAt.getTime(), now.getTime());
            throw new JwtAuthenticationException(messageSource.buildMessage("errors.token.expire", PeriodFormat.getDefault().print(period)));
        } catch (IllegalArgumentException exception) {
            throw new AuthenticationServiceException(StringUtils.EMPTY);
        }
    }

    private String jwtGenerator(String username, Long expireTimeMs, String secretWord) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", fromAuthoritiesToListOfRoles(userDetailsService.loadUserByUsername(username).getAuthorities()));
        Date now = new Date();
        Date expireTime = new Date(now.getTime() + expireTimeMs);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expireTime)
                .signWith(SignatureAlgorithm.HS256, secretWord)
                .compact();
    }

    private List<String> fromAuthoritiesToListOfRoles(Collection<? extends GrantedAuthority> userAuthorities) {
        return userAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }
}
