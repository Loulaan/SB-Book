package controller;

import dto.AuthTokensDTO;
import dto.payload.request.*;
import exception.ApiException.AccountNotActivatedException;
import exception.ApiException.UserNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.RegistrationAuthorizationService;

import javax.security.auth.login.AccountNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping(value = "api/v1/users/")
public class AuthController {

    private final RegistrationAuthorizationService registrationAuthorizationService;

    @PostMapping(value = "registration")
    public ResponseEntity<String> register(@RequestBody @Valid RegistrationRequestDTO request) {
        registrationAuthorizationService.registration(request);
        return ResponseEntity.ok("You Successfully registered,please check your email to verify account!");
    }

    @PostMapping(value = "login")
    public ResponseEntity<AuthTokensDTO> login(@RequestBody @Valid AuthRequestDTO request) throws UserNotFoundException, AccountNotActivatedException {
        registrationAuthorizationService.authenticateUser(request);
        return ResponseEntity.ok(registrationAuthorizationService.createAuthTokens(request));
    }

    @PostMapping(value = "refresh_token")
    public ResponseEntity<AuthTokensDTO> refreshToken(@RequestBody AuthTokensDTO request) throws UserNotFoundException {
        return ResponseEntity.ok(registrationAuthorizationService.refreshAuthTokens(request));
    }

    @GetMapping(value = "confirm_email")
    public ResponseEntity<String> confirmEmail(@RequestParam String token) {
        registrationAuthorizationService.activateUserAccount(token);
        return ResponseEntity.ok("You successfully activate account,now you can login!");
    }

    @GetMapping(value = "refresh_verification_token")
    public ResponseEntity<String> sendVerificationLinkAgain(@RequestParam String token) {
        registrationAuthorizationService.sendVerificationTokenAgain(token);
        return ResponseEntity.ok("We send new mail with instruction to your email please follow steps inside!");
    }

    @PostMapping(value = "password/forgot")
    public ResponseEntity<String> sendResetPasswordLink(@RequestBody ResetPasswordGetRequestDTO request) throws UserNotFoundException, AccountNotFoundException {
        registrationAuthorizationService.sendVerificationToken("/api/v1/users/password/reset", request);
        return ResponseEntity.ok("We send reset link to your email,check it!");
    }

    @GetMapping(value = "password/reset")
    public ResponseEntity<String> updateUserWithNewNewPasswordForm(@RequestParam String token) {
        return ResponseEntity.ok("Insert password,confirm it and post");
    }

    @PostMapping(value = "password/reset")
    public ResponseEntity<String> updateUserWithNewNewPassword(@RequestParam String token, @RequestBody @Valid ResetPasswordPostRequestDTO request) {
        registrationAuthorizationService.resetPassword(token, request.getPassword());
        return ResponseEntity.ok("You successfully change password!");
    }

    @PostMapping(value = "password/change")
    public ResponseEntity<String> changePassword(HttpServletRequest request, @RequestBody @Valid ChangePasswordRequestDTO changePasswordRequestDTO) throws UserNotFoundException {
        registrationAuthorizationService.changePassword(changePasswordRequestDTO, request);
        return ResponseEntity.ok("You successfully change password!");
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) throws UserNotFoundException {
        registrationAuthorizationService.deleteAccount(id);
        return ResponseEntity.ok("Your account was successfully deleted!");
    }
}
