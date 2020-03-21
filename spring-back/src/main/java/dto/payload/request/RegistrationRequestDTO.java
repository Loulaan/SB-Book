package dto.payload.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import validation.annotation.NotEmptyOrBlank;
import lombok.Data;
import validation.annotation.PasswordMatchesRegexp;

import javax.validation.constraints.Pattern;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistrationRequestDTO {

    @NotEmptyOrBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{6,}$", message = "{not.correct.username}")
    private String userName;

    @NotEmptyOrBlank
    private String firstName;

    @NotEmptyOrBlank
    private String lastName;

    @NotEmptyOrBlank
    @PasswordMatchesRegexp
    private String password;

    @NotEmptyOrBlank
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "{not.correct.email}")
    private String email;
}

