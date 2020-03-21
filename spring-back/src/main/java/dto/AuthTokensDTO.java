package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthTokensDTO {

    private String token;
    private String refreshToken;

    public AuthTokensDTO(Map<String, String> tokenMap) {
        token = tokenMap.get("token");
        refreshToken = tokenMap.get("refreshToken");
    }
}
