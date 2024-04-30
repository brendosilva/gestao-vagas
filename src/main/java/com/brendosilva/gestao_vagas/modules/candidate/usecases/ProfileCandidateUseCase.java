package com.brendosilva.gestao_vagas.modules.candidate.usecases;

import com.brendosilva.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import com.brendosilva.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileCandidateUseCase {
    private final CandidateRepository candidateRepository;
    public ProfileCandidateResponseDTO execute(UUID id) {
        var candidate = this.candidateRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));


        return ProfileCandidateResponseDTO.builder()
                .name(candidate.getName())
                .username(candidate.getUsername())
                .email(candidate.getEmail())
                .description(candidate.getDescription())
                .id(candidate.getId())
                .build();

    }
}
