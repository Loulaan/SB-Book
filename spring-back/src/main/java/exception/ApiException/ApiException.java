package exception.ApiException;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = false)
public class ApiException extends RuntimeException {

    private static final long serialVersionUID = -1944627054514881384L;
    protected String message;
    protected String errorCode;
    protected String exceptionPropertyKey;
    protected Map<String, String> userParameters = new HashMap();
    protected HashMap<String, String> argsDetail = new HashMap<>();
    protected Map<String, String> errors = new HashMap<>();

    public ApiException() {
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable throwable) {
        super(message, throwable);
    }
}

