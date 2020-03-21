package com.hronosf.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponseDTO {

    private String message;
    private Map<String, Object> errors;

    public void setErrors(Map<String, Object> info) {
        if (errors == null) {
            errors = new HashMap<>();
        }
        errors.putAll(info);
    }
}
