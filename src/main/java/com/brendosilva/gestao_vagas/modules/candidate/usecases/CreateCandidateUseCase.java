package com.brendosilva.gestao_vagas.modules.candidate.usecases;

import com.brendosilva.gestao_vagas.exceptions.UserFoundException;
import com.brendosilva.gestao_vagas.modules.candidate.entities.CandidateEntity;
import com.brendosilva.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCandidateUseCase {

    private final PasswordEncoder passwordEncoder;

    private final CandidateRepository candidateRepository;

    public CandidateEntity execute(CandidateEntity entity){
        var candidateExists = this.candidateRepository.findByUsernameOrEmail(entity.getUsername(), entity.getEmail());
        candidateExists.ifPresent((user) -> {
            throw new UserFoundException("Candidate Already exists");
        });

        var password = passwordEncoder.encode(entity.getPassword());
        entity.setPassword(password);

        return this.candidateRepository.save(entity);
    }
}
