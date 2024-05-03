package com.brendosilva.gestao_vagas.modules.candidate.usecases;

import com.brendosilva.gestao_vagas.exceptions.JobNotFoundException;
import com.brendosilva.gestao_vagas.exceptions.UserNotFoundException;
import com.brendosilva.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import com.brendosilva.gestao_vagas.modules.candidate.entities.CandidateEntity;
import com.brendosilva.gestao_vagas.modules.candidate.repositories.ApplyJobRepository;
import com.brendosilva.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import com.brendosilva.gestao_vagas.modules.company.entities.JobEntity;
import com.brendosilva.gestao_vagas.modules.company.repositories.JobRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApplyJobCandidateUseCaseTest {
    @InjectMocks
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;
   @Mock
    private CandidateRepository candidateRepository;
    @Mock
   private JobRepository jobRepository;
    @Mock
    private ApplyJobRepository applyJobRepository;

    @Test
    @DisplayName(value = "should not be able to apply job with candidate not found")
    public void shouldNotBeAbleToApplyJobWithCandidateNotFound() {
        try {
            applyJobCandidateUseCase.execute(null, null);
        } catch (Exception e) {
            Assertions.assertThat(e).isInstanceOf(UserNotFoundException.class);
        }
    }

    @Test
    @DisplayName("should not be able to apply job with job not found")
    public void shouldNotBeAbleToApplyJobWithJobNotFound() {
        var idCandidate = UUID.randomUUID();
        var candidate = new CandidateEntity();
        candidate.setId(idCandidate);
        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(candidate));
        try {
            applyJobCandidateUseCase.execute(idCandidate, null);
        } catch (Exception e) {
            Assertions.assertThat(e).isInstanceOf(JobNotFoundException.class);
        }
    }

    @Test
    @DisplayName("Should be able to create a new apply job ")
    public void shouldBeAbleToCrateANewApplyJob() {
       var idCandidate = UUID.randomUUID();
       var idJob = UUID.randomUUID();
       var applyJob = ApplyJobEntity.builder().candidateId(idCandidate).jobId(idJob).build();
       var applyJobCreate = ApplyJobEntity.builder().id(UUID.randomUUID()).build();

       when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(new CandidateEntity()));
       when(jobRepository.findById(idJob)).thenReturn(Optional.of(new JobEntity()));
       when(applyJobRepository.save(applyJob)).thenReturn(applyJobCreate);

       var result = applyJobCandidateUseCase.execute(idCandidate,idJob);

       Assertions.assertThat(result).hasFieldOrProperty("id");
       assertNotNull(result.getId());
    }

}