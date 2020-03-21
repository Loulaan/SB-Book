package dto.payload.request;

import validation.annotation.NotEmptyOrBlank;
import validation.annotation.PasswordsMatches;
import lombok.Data;
import validation.annotation.PasswordMatchesRegexp;

@Data
@PasswordsMatches
public class ChangePasswordRequestDTO {

    @NotEmptyOrBlank
    private String oldPassword;

    @NotEmptyOrBlank
    @PasswordMatchesRegexp
    private String newPassword;

    @NotEmptyOrBlank
    private String confirmPassword;
}
