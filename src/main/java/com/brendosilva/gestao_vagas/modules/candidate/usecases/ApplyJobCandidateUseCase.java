package com.brendosilva.gestao_vagas.modules.candidate.usecases;

import com.brendosilva.gestao_vagas.exceptions.JobNotFoundException;
import com.brendosilva.gestao_vagas.exceptions.UserNotFoundException;
import com.brendosilva.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import com.brendosilva.gestao_vagas.modules.candidate.repositories.ApplyJobRepository;
import com.brendosilva.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import com.brendosilva.gestao_vagas.modules.company.entities.JobEntity;
import com.brendosilva.gestao_vagas.modules.company.repositories.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApplyJobCandidateUseCase {
    private final CandidateRepository candidateRepository;
    private final JobRepository jobRepository;
    private final ApplyJobRepository applyJobRepository;
    public ApplyJobEntity execute(UUID idCandidate, UUID idJob) {
        this.candidateRepository.findById(idCandidate).orElseThrow(() -> new UserNotFoundException("User not found"));
        this.jobRepository.findById(idJob).orElseThrow(()-> new JobNotFoundException("Job not found"));
        var applyJob = ApplyJobEntity.builder()
                .candidateId(idCandidate)
                .jobId(idJob)
                .build();

        return applyJobRepository.save(applyJob);
    }
}
