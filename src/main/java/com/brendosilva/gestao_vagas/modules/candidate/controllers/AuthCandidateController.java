package com.brendosilva.gestao_vagas.modules.candidate.controllers;

import com.brendosilva.gestao_vagas.modules.candidate.dto.AuthCandidateRequetDTO;
import com.brendosilva.gestao_vagas.modules.candidate.usecases.AuthCandidateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidate")
@RequiredArgsConstructor
public class AuthCandidateController {
    private final AuthCandidateUseCase authCandidateUseCase;

    @PostMapping("/auth")
    public ResponseEntity<Object>  auth(@RequestBody AuthCandidateRequetDTO dto){
        try {
            var token =  authCandidateUseCase.execute(dto);
            return ResponseEntity.ok().body(token);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
