package com.brendosilva.gestao_vagas.modules.company.usecases;


import com.brendosilva.gestao_vagas.modules.company.entities.JobEntity;
import com.brendosilva.gestao_vagas.modules.company.repositories.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateJobUseCase {

    private final JobRepository jobRepository;

    public JobEntity execute(JobEntity entity) {
        return this.jobRepository.save(entity);
    }
}
