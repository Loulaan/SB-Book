package dto.payload.request;

import validation.annotation.PasswordsMatches;
import lombok.Data;
import validation.annotation.NotEmptyOrBlank;
import validation.annotation.PasswordMatchesRegexp;

@Data
@PasswordsMatches
public class ResetPasswordPostRequestDTO {

    @NotEmptyOrBlank
    @PasswordMatchesRegexp
    private String password;

    @NotEmptyOrBlank
    private String confirmPassword;
}
