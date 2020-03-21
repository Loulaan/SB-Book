package dto.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponseDTO {

    private Map<String, Object> errors = new HashMap<>();


    public ExceptionResponseDTO(String errorMessage) {
        String[] summaryToDetailsMessage = errorMessage.split(":");
        errors.put(summaryToDetailsMessage[0], summaryToDetailsMessage[1]);
    }

    public ExceptionResponseDTO(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        if (fieldErrors.isEmpty()) {
            errors.put("errors", ex.getBindingResult()
                    .getAllErrors()
                    .stream()
                    .collect(Collectors.toMap(DefaultMessageSourceResolvable::getCode, ObjectError::getDefaultMessage)));
        } else {
            errors.put("errors", fieldErrors
                    .stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)));
        }
    }
}
