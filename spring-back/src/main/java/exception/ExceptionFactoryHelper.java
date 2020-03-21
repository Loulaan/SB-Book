package exception;

import exception.ApiException.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class ExceptionFactoryHelper {

    private final MessageSource messageSource;

    public String buildMessage(String key) {
        return messageSource.getMessage(key, null, Locale.getDefault());
    }

    public String buildMessage(String key, Object... arg) {
        return messageSource.getMessage(key, arg, Locale.getDefault());
    }

    public <T extends ApiException> T buildDetailedMessage(T exception, Object... arg) {
        String details = messageSource.getMessage(exception.getExceptionPropertyKey(), arg, Locale.getDefault());
        exception.setMessage(details);
        return exception;
    }
}
