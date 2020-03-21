package dto.payload.request;

import lombok.Data;
import validation.annotation.NotEmptyOrBlank;

@Data
public class AuthRequestDTO {

    @NotEmptyOrBlank
    private String username;

    @NotEmptyOrBlank
    private String password;
}
