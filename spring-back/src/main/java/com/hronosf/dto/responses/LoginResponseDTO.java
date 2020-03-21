package com.hronosf.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class LoginResponseDTO {

    Map<String, String> tokens;
}
