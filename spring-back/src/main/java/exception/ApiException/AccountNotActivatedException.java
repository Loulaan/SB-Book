package exception.ApiException;

import org.springframework.security.authentication.AccountStatusException;

public class AccountNotActivatedException extends AccountStatusException {

    public AccountNotActivatedException(String s) {
        super(s);
    }

}
