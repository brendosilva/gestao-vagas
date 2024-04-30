package com.brendosilva.gestao_vagas.modules.candidate.controllers;

import com.brendosilva.gestao_vagas.exceptions.UserFoundException;
import com.brendosilva.gestao_vagas.modules.candidate.entities.CandidateEntity;
import com.brendosilva.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import com.brendosilva.gestao_vagas.modules.candidate.usecases.CreateCandidateUseCase;
import com.brendosilva.gestao_vagas.modules.candidate.usecases.ProfileCandidateUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/candidate")
@RequiredArgsConstructor
public class CandidateController {
    private final CreateCandidateUseCase createCandidateUseCase;
    private final ProfileCandidateUseCase profileCandidateUseCase;
    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity entity) {
        try {
            var result = this.createCandidateUseCase.execute(entity);
            return ResponseEntity.ok().body(result);
        } catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ResponseEntity<Object> get(HttpServletRequest request){
        var idCandidate = request.getAttribute("candidate_id");
        try {
            var profile = this.profileCandidateUseCase.execute(UUID.fromString(idCandidate.toString()));
            return ResponseEntity.ok().body(profile);
        } catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
