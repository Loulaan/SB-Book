package com.hronosf.converter.impl;

import com.hronosf.converter.ExceptionHandlerFactory;
import com.hronosf.dto.responses.ExceptionResponseDTO;
import com.hronosf.exception.APIException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Component
@RequiredArgsConstructor
public final class ExceptionHandlerFactoryImpl implements ExceptionHandlerFactory {

    private final MessageSource messageSource;

    @SuppressWarnings("unchecked")
    public <T extends APIException> T provideExceptionWithDetails(T exception, Object errorDetails, Object... args) {
        String localizedMessage = messageSource.getMessage(exception.getPropertyKey(), args, Locale.getDefault());
        exception.setMessage(localizedMessage);
        if (errorDetails != null) {
            if (errorDetails instanceof Map) {
                Map<String, Object> errors = (HashMap<String, Object>) errorDetails;
                errors.replaceAll((k, v) -> messageSource.getMessage((String) v, null, Locale.getDefault()));
                exception.setErrorDetails(errors);
            } else {
                exception.setErrorDetails(exception.getReason(), errorDetails);
            }
        }
        return exception;
    }

    public <T extends APIException> ExceptionResponseDTO convertExceptionToResponse(T exception) {
        ExceptionResponseDTO response = new ExceptionResponseDTO();

        if (exception.getErrorDetails() != null) {
            response.setErrors(exception.getErrorDetails());
        }

        if (exception.getMessage() != null) {
            response.setMessage(exception.getMessage());
        }

        return response;
    }
}
