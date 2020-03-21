package security.jwt.exception;

import org.springframework.security.core.AuthenticationException;

public class VerificationTokenExpireException extends AuthenticationException {

    public VerificationTokenExpireException(String msg) {
        super(msg);
    }
}
