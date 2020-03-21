package com.hronosf.rest;

import com.hronosf.dto.requests.RegistrationRequestDTO;
import com.hronosf.dto.responses.RegistrationResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.hronosf.service.AccountService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final AccountService accountService;

    @PostMapping(value = "/test")
    public ResponseEntity<RegistrationResponseDTO> test(@Valid @RequestBody RegistrationRequestDTO request) {
        return ResponseEntity.ok(accountService.createPersonAccount(request));
    }
}
