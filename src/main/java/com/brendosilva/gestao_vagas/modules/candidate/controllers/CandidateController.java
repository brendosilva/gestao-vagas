package com.brendosilva.gestao_vagas.modules.candidate.controllers;

import com.brendosilva.gestao_vagas.exceptions.UserFoundException;
import com.brendosilva.gestao_vagas.modules.candidate.entities.CandidateEntity;
import com.brendosilva.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import com.brendosilva.gestao_vagas.modules.candidate.usecases.CreateCandidateUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidate")
@RequiredArgsConstructor
public class CandidateController {
    private final CreateCandidateUseCase createCandidateUseCase;
    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity entity) {
        try {
            var result = this.createCandidateUseCase.execute(entity);
            return ResponseEntity.ok().body(result);
        } catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
