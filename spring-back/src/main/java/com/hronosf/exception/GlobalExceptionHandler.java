package com.hronosf.exception;

import com.hronosf.converter.impl.ExceptionHandlerFactoryImpl;
import com.hronosf.exception.specified.EmailSenderException;
import com.hronosf.dto.responses.ExceptionResponseDTO;
import com.hronosf.exception.specified.AccountServiceException;
import com.hronosf.exception.specified.JwtServiceException;
import com.hronosf.exception.specified.SecurityTokenServiceException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormat;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.mail.MessagingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final ExceptionHandlerFactoryImpl exceptionHandlerFactory;

    //  Patter to extract Date and Time from ExpiredJwtException to specify error details in response
    private static final Pattern timeInJwtExpireExceptionPattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\+\\d{4}");

    /**
     * Processing of ExpiredJwtException: used for including information about expiration time of JWT-token in response.
     *
     * @param exception - ExpiredJwtException thrown when JWT token expired.
     * @return - ExceptionResponseDTO contains necessary info about occurred exception.
     */
    @SneakyThrows
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ExceptionResponseDTO> handleJwtTokenException(ExpiredJwtException exception) {

        Matcher matcher = timeInJwtExpireExceptionPattern.matcher(exception.getMessage());
        Date expiredAt = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").parse(matcher.group(0).replace("T", " "));
        Period period = new Period(expiredAt.getTime(), new Date().getTime());

        JwtServiceException exceptionWithDetails = exceptionHandlerFactory
                .provideExceptionWithDetails(new JwtServiceException(), PeriodFormat.getDefault().print(period), (Object) null);

        return ResponseEntity.badRequest().body(exceptionHandlerFactory.convertExceptionToResponse(exceptionWithDetails));
    }

    @ExceptionHandler({AccountServiceException.class, SecurityTokenServiceException.class})
    public <T extends APIException> ResponseEntity<ExceptionResponseDTO> handleAPIException(T exception) {
        T exceptionWithDetails = exceptionHandlerFactory.provideExceptionWithDetails(exception, null, (Object) null);
        return ResponseEntity.badRequest().body(exceptionHandlerFactory.convertExceptionToResponse(exceptionWithDetails));
    }

    @ExceptionHandler({MessagingException.class})
    public ResponseEntity<ExceptionResponseDTO> handleMessagingException(MessagingException exception) {
        EmailSenderException newException = new EmailSenderException();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionHandlerFactory.convertExceptionToResponse(newException));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> errors = new HashMap<>();
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        if (fieldErrors.isEmpty()) {
            errors.putAll(ex.getBindingResult()
                    .getAllErrors()
                    .stream()
                    .collect(Collectors.toMap(DefaultMessageSourceResolvable::getCode, ObjectError::getDefaultMessage)));
        } else {
            errors.putAll(fieldErrors
                    .stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)));
        }
        AccountServiceException exception = exceptionHandlerFactory.provideExceptionWithDetails(new AccountServiceException("validation-errors.base"), errors, (Object) null);

        return ResponseEntity.badRequest().body(exceptionHandlerFactory.convertExceptionToResponse(exception));
    }
}
