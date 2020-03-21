package com.hronosf.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = false)
public abstract class APIException extends RuntimeException {

    private static final long serialVersionUID = -1944627054514881384L;

    protected String reason;
    protected String message;
    protected String propertyKey;
    protected Map<String, Object> errorDetails = new HashMap<>();

    public APIException() {
    }

    public APIException(String message) {
        super(message);
    }

    public APIException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public void setErrorDetails(String reason, Object details) {
        if (errorDetails == null) {
            errorDetails = new HashMap<>();
        }
        errorDetails.put(reason, details);
    }
}
