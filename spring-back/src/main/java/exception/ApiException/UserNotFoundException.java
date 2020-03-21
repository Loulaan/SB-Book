package exception.ApiException;

import java.sql.SQLException;

public class UserNotFoundException extends SQLException {
    public UserNotFoundException(String msg) {
        super(msg);
    }
}
