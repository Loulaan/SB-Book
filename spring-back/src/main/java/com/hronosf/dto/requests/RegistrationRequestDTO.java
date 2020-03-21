package com.hronosf.dto.requests;

import com.hronosf.dto.APIRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.hronosf.validator.annotations.NotEmptyOrBlank;
import com.hronosf.validator.annotations.PasswordTemplateMatch;

import javax.validation.constraints.Pattern;

@Data
@EqualsAndHashCode(callSuper = true)
public class RegistrationRequestDTO extends APIRequest {

    @NotEmptyOrBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{6,}$", message = "validation-errors.notMatchUsernameRegexp")
    private String userName;

    @NotEmptyOrBlank
    private String firstName;

    private String middleName;

    @NotEmptyOrBlank
    private String lastName;

    @NotEmptyOrBlank
    @PasswordTemplateMatch
    private String password;

    @NotEmptyOrBlank
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "validation-errors.notCorrectEmail")
    private String email;

    @NotEmptyOrBlank
    @Pattern(regexp = "^(\\+7|7|8)+([0-9]){10}", message = "validation-errors.notMatchPhoneRegexp")
    private String phoneNumber;
}
