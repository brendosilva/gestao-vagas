package com.brendosilva.gestao_vagas.modules.candidate.controllers;

import com.brendosilva.gestao_vagas.modules.candidate.entities.CandidateEntity;
import com.brendosilva.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidate")
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateRepository candidateRepository;

    @PostMapping("/")
    public CandidateEntity create(@Valid @RequestBody CandidateEntity entity) {
        return this.candidateRepository.save(entity);
    }
}
