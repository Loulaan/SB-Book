package com.hronosf.exception.specified;

import com.hronosf.exception.APIException;

import javax.annotation.PostConstruct;

public class JwtServiceException extends APIException {

    public JwtServiceException() {
        this.propertyKey = "api-errors.tokenIsExpire";
    }

    public JwtServiceException(String message) {
        super(message);
    }

    @PostConstruct
    public void setMessageKey() {
        this.reason = "token_validation";
    }
}
