package com.hronosf.dto.requests;

import lombok.Data;
import com.hronosf.validator.annotations.PasswordsMatches;
import com.hronosf.validator.annotations.PasswordTemplateMatch;

@Data
@PasswordsMatches
public class ResetPasswordPostRequestDTO {

    @PasswordTemplateMatch
    private String password;

    private String confirmPassword;
}
