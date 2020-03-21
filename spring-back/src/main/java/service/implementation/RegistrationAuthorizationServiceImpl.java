package service.implementation;

import dto.AuthTokensDTO;
import dto.payload.request.AuthRequestDTO;
import dto.payload.request.ChangePasswordRequestDTO;
import dto.payload.request.ResetPasswordGetRequestDTO;
import exception.ApiException.UserNotFoundException;
import exception.ExceptionFactoryHelper;
import model.entity.User;
import model.entity.VerificationToken;
import org.springframework.transaction.annotation.Transactional;
import security.ApiException.VerificationTokenStillValid;
import security.event.SendTokenByEmailActionEvent;
import security.jwt.service.JwtTokenService;
import security.service.TokenVerificationService;
import service.RegistrationAuthorizationService;
import service.UserService;
import dto.payload.request.RegistrationRequestDTO;
import exception.ApiException.AccountNotActivatedException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class RegistrationAuthorizationServiceImpl implements RegistrationAuthorizationService {

    private final UserService userService;
    private final JwtTokenService jwtTokenService;
    private final TokenVerificationService tokenVerificationService;

    private final ExceptionFactoryHelper messageSource;
    private final AuthenticationManager authenticationManager;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    @Override
    public void registration(RegistrationRequestDTO request) {
        User savedUser = userService.save(userService.createUser(request));
        sendVerificationToken("/api/v1/users/confirm_email", savedUser);
    }

    @Override
    public AuthTokensDTO createAuthTokens(AuthRequestDTO request) throws UserNotFoundException {
        return jwtTokenService.createAuthTokens(request.getUsername());
    }

    @Override
    public AuthTokensDTO refreshAuthTokens(AuthTokensDTO request) throws UserNotFoundException {
        return jwtTokenService.refreshAuthTokens(request.getRefreshToken(), request.getToken());
    }

    @Override
    public void sendVerificationToken(String redirectTo, ResetPasswordGetRequestDTO request) throws UserNotFoundException {
        sendVerificationToken(redirectTo, userService.getUserIfAccountActivated(request.getEmail()));
    }

    @Override
    public void sendVerificationTokenAgain(String oldToken) {
        VerificationToken oldTokenEntity = tokenVerificationService.getTokenEntity(oldToken);
        if (!tokenVerificationService.isTokenExpired(oldTokenEntity)) {
            throw new VerificationTokenStillValid(messageSource.buildMessage("errors.token.still.valid"));
        } else {
            User user = oldTokenEntity.getUser();
            if (!user.isEnabled()) {
                sendVerificationToken("/api/v1/users/confirm_email", user);
            } else {
                sendVerificationToken("/api/v1/users/password/reset", user);
            }
        }
    }

    @Override
    public void authenticateUser(AuthRequestDTO request) throws UserNotFoundException {
        try {
            if (!userService.findByUserName(request.getUsername()).isEnabled()) {
                throw new AccountNotActivatedException(messageSource.buildMessage("errors.account.not.activated"));
            }
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (BadCredentialsException exception) {
            throw new BadCredentialsException(messageSource.buildMessage("errors.bad.credentials"));
        }
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        VerificationToken tokenFromBase = tokenVerificationService.getTokenEntity(token);
        if (tokenVerificationService.throwIfTokenNotValid(tokenFromBase)) {
            userService.changePassword(newPassword, tokenFromBase.getUser());
            tokenVerificationService.delete(tokenFromBase);
        }
    }

    @Override
    public void activateUserAccount(String tokenString) {
        VerificationToken token = tokenVerificationService.getTokenEntity(tokenString);
        if (tokenVerificationService.throwIfTokenNotValid(token)) {
            User user = token.getUser();
            tokenVerificationService.delete(token);
            user.setEnabled(true);
            userService.save(user);
        }
    }

    @Override
    public void deleteAccount(Long id) throws UserNotFoundException {
        userService.delete(id);
    }


    @Override
    public void changePassword(ChangePasswordRequestDTO changePasswordRequestDTO, HttpServletRequest request) throws UserNotFoundException {
        UserDetails user = jwtTokenService.getUserDetailsByToken(request);
        if (!userService.isPasswordValid(user.getPassword(), changePasswordRequestDTO.getOldPassword())) {
            throw new BadCredentialsException(messageSource.buildMessage("errors.password.changing.wrong"));
        }
        userService.changePassword(changePasswordRequestDTO.getNewPassword(), user.getUsername());
    }

    private void sendVerificationToken(String redirectTo, User user) {
        UriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentContextPath().path(redirectTo);
        SendTokenByEmailActionEvent sendToken = new SendTokenByEmailActionEvent(user, urlBuilder);
        applicationEventPublisher.publishEvent(sendToken);
    }
}
