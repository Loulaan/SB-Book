package com.hronosf.exception.specified;

import com.hronosf.exception.APIException;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AccountServiceException extends APIException {

    public AccountServiceException() {
        this.propertyKey = "api-errors.userNotFound";
        this.reason = "account_validation";
    }

    public AccountServiceException(String propertyKey) {
        this.propertyKey = propertyKey;
        this.reason = "account_validation";
    }

}
