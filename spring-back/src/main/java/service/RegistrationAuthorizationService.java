package service;

import dto.AuthTokensDTO;
import dto.payload.request.AuthRequestDTO;
import dto.payload.request.ChangePasswordRequestDTO;
import dto.payload.request.ResetPasswordGetRequestDTO;
import exception.ApiException.UserNotFoundException;
import dto.payload.request.RegistrationRequestDTO;
import exception.ApiException.AccountNotActivatedException;

import javax.security.auth.login.AccountNotFoundException;
import javax.servlet.http.HttpServletRequest;

public interface RegistrationAuthorizationService {

    void registration(RegistrationRequestDTO request);

    AuthTokensDTO createAuthTokens(AuthRequestDTO request) throws UserNotFoundException;

    AuthTokensDTO refreshAuthTokens(AuthTokensDTO request) throws UserNotFoundException;

    void sendVerificationToken(String redirectTo, ResetPasswordGetRequestDTO request) throws UserNotFoundException, AccountNotFoundException;

    void sendVerificationTokenAgain(String oldToken);

    void authenticateUser(AuthRequestDTO request) throws UserNotFoundException, AccountNotActivatedException;

    void resetPassword(String token, String newPassword);

    void activateUserAccount(String token);

    void deleteAccount(Long id) throws UserNotFoundException;

    void changePassword(ChangePasswordRequestDTO changePasswordRequestDTO, HttpServletRequest request) throws UserNotFoundException;
}
