package com.hronosf.dto.requests;

import com.hronosf.dto.APIRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.hronosf.validator.annotations.NotEmptyOrBlank;
import com.hronosf.validator.annotations.PasswordsMatches;
import com.hronosf.validator.annotations.PasswordTemplateMatch;

@Data
@PasswordsMatches
@EqualsAndHashCode(callSuper = true)
public class ChangePasswordRequestDTO extends APIRequest {

    @NotEmptyOrBlank
    private String oldPassword;

    @NotEmptyOrBlank
    @PasswordTemplateMatch
    private String newPassword;

    @NotEmptyOrBlank
    private String confirmPassword;
}
