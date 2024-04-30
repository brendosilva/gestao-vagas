package com.brendosilva.gestao_vagas.modules.company.controllers;

import com.brendosilva.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import com.brendosilva.gestao_vagas.modules.company.usecases.AuthCompanyUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class AuthCompanyController {
    private final AuthCompanyUseCase authCompanyUseCase;


    @PostMapping("/auth")
    public ResponseEntity<Object> create (@RequestBody AuthCompanyDTO dto) {
        try {
            var result = this.authCompanyUseCase.execute(dto);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}
