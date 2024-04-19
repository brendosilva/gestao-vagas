package com.brendosilva.gestao_vagas.modules.company.controllers;

import com.brendosilva.gestao_vagas.modules.company.entities.JobEntity;
import com.brendosilva.gestao_vagas.modules.company.usecases.CreateJobUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/job")
@RequiredArgsConstructor
public class JobController {

    private final CreateJobUseCase createJobUseCase;

    @PostMapping("/")
    public JobEntity create(@Valid @RequestBody JobEntity entity) {
        return this.createJobUseCase.execute(entity);
    }

}
