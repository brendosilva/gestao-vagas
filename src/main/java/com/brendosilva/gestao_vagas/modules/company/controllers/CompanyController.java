package com.brendosilva.gestao_vagas.modules.company.controllers;

import com.brendosilva.gestao_vagas.modules.company.entities.CompanyEntity;
import com.brendosilva.gestao_vagas.modules.company.usecases.CreateCompanyUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {
    private final CreateCompanyUseCase createCompanyUseCase;

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody CompanyEntity entity) {
        try {
            CompanyEntity execute = this.createCompanyUseCase.execute(entity);
            return ResponseEntity.ok().body(execute);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
