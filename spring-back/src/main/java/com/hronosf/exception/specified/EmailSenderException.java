package com.hronosf.exception.specified;

import com.hronosf.exception.APIException;

public class EmailSenderException extends APIException {

    public EmailSenderException() {
        this.message = "api-errors.emailSender";
    }
}
