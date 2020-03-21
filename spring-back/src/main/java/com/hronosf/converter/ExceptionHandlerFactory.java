package com.hronosf.converter;

import com.hronosf.dto.responses.ExceptionResponseDTO;
import com.hronosf.exception.APIException;

public interface ExceptionHandlerFactory {

    /**
     * Used for providing exception with details:
     * Should take localized message based on Locale.getDefault() method,
     * fill errorDetails and enrich exception with it.
     *
     * @param exception    - APIException.
     * @param errorDetails -  necessary information about occurred exception.
     * @param args         - used if message in .properties files contains parameters.
     * @param <T>          - class inherits APIException.
     * @return - same exception enriched by details.
     */
    <T extends APIException> T provideExceptionWithDetails(T exception, Object errorDetails, Object... args);

    /**
     * Used for converting occurred exception to DTO format:
     * Should return information about it to client.
     *
     * @param exception - APIException.
     * @param <T>       - class inherits APIException.
     * @return - instance of ExceptionResponseDTO contains necessary info about exception.
     */
    <T extends APIException> ExceptionResponseDTO convertExceptionToResponse(T exception);
}
