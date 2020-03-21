package com.hronosf.exception.specified;

import com.hronosf.exception.APIException;

import javax.annotation.PostConstruct;

public class SecurityTokenServiceException extends APIException {

    public SecurityTokenServiceException() {
        this.propertyKey = "api-errors.base";
    }

    public SecurityTokenServiceException(String message) {
        super(message);
    }

    @PostConstruct
    public void setMessageKey() {
        this.reason = "verification_token_validation";
    }
}
