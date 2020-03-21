package com.hronosf.security.jwt.service;

import com.hronosf.dto.responses.LoginResponseDTO;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface JwtTokenService {

    /**
     * Used to generate simple and refresh JWT-token in authentication process.
     *
     * @param userName - userName of person to login.
     * @return - instance of LoginResponseDTO contains simple and refresh JWT tokens.
     */
    LoginResponseDTO generateJwtTokens(String userName);

    /**
     * Used to regenerate simple and refresh JWT-token when simple token is expire:
     * Should be called by UI.
     *
     * @param refreshToken - not expired JWT refresh token or else throws ExpiredJwtException.
     * @param token        - expired JWT-token or else JwtServiceException with token not expired message.
     * @return - instance of LoginResponseDTO contains simple and refresh JWT tokens.
     */
    LoginResponseDTO refreshJwtTokens(String refreshToken, String token);

    /**
     * Used to provide authentication by Spring Security in JwtAuthenticationFilter.
     *
     * @param token -not expired JWT-token or else JwtServiceException with token expire message.
     * @return - Authentication object.
     */
    Authentication getAuthentication(String token);

    /**
     * Used to extract JWT-token from HttpRequest headers.
     * Should get Authorization header and remove prefix.
     *
     * @param request - HttpServletRequest.
     * @return - JWT-token provided in request.
     */
    String extractJwtToken(HttpServletRequest request);

    /**
     * Used to validate token:
     * Should check expiration date.
     *
     * @param token - JWT-token.
     * @return true if not expired or throws ExpiredJwtException.
     */
    boolean isTokenValid(String token);

    /**
     * Used to extract userName of logged Person from JWT-token.
     *
     * @param request - HttpServletRequest
     * @return - userName of logged user or throws ExpiredJwtException if token expire.
     */
    String extractUserNameFromJwtToken(HttpServletRequest request);
}
