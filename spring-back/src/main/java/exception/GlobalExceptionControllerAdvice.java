package exception;

import dto.payload.response.ExceptionResponseDTO;
import exception.ApiException.RoleNotFoundException;
import exception.ApiException.UserNotFoundException;
import security.ApiException.VerificationTokenNotFoundException;
import security.jwt.exception.JwtAuthenticationException;
import security.jwt.exception.VerificationTokenExpireException;
import exception.ApiException.AccountNotActivatedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import security.ApiException.VerificationTokenStillValid;

import javax.security.auth.login.AccountNotFoundException;
import java.sql.SQLException;

@Component
@RestControllerAdvice
public class GlobalExceptionControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({SQLException.class, VerificationTokenExpireException.class, JwtAuthenticationException.class,
            BadCredentialsException.class, UserNotFoundException.class, AuthenticationCredentialsNotFoundException.class,
            AccountNotActivatedException.class, AccountNotFoundException.class, VerificationTokenNotFoundException.class,
            VerificationTokenStillValid.class})
    public ResponseEntity<ExceptionResponseDTO> handleApiErrors(Exception exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponseDTO(exception.getMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new ExceptionResponseDTO(ex), HttpStatus.valueOf(status.value()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionResponseDTO> handleAPiForbidden(AccessDeniedException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ExceptionResponseDTO(exception.getMessage()));
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ExceptionResponseDTO> handlePartialSuccess(RoleNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.MULTI_STATUS).body(new ExceptionResponseDTO(exception.getMessage()));
    }
}
