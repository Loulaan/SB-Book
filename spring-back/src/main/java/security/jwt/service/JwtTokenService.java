package security.jwt.service;

import dto.AuthTokensDTO;
import exception.ApiException.UserNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;

public interface JwtTokenService {

    AuthTokensDTO createAuthTokens(String username) throws UserNotFoundException;

    AuthTokensDTO refreshAuthTokens(String refreshToken, String token) throws UserNotFoundException;

    Authentication getAuthentication(String token);

    boolean isTokenValid(String token);

    String handleToken(HttpServletRequest request);

    UserDetails getUserDetailsByToken(HttpServletRequest request) throws UserNotFoundException;
}
