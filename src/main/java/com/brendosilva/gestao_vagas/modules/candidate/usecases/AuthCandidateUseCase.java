package com.brendosilva.gestao_vagas.modules.candidate.usecases;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.brendosilva.gestao_vagas.modules.candidate.dto.AuthCandidateRequetDTO;
import com.brendosilva.gestao_vagas.modules.candidate.dto.AuthCandidateResponseDTO;
import com.brendosilva.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthCandidateUseCase {
    private final CandidateRepository candidateRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${security.token.secret.candidate}")
    private String secretKey;
    public AuthCandidateResponseDTO execute(AuthCandidateRequetDTO dto) throws AuthenticationException {
        var expiresIn = Instant.now().plus(Duration.ofMinutes(10));
        var candidate = candidateRepository.findByUsername(dto.username())
                .orElseThrow(() -> new UsernameNotFoundException("username / password not found"));
        var matchers = passwordEncoder.matches(dto.password(), candidate.getPassword());
        if (!matchers) {
            throw new AuthenticationException();
        }
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var token = JWT
                .create()
                .withIssuer("javagas")
                .withExpiresAt(expiresIn)
                .withClaim("roles", List.of("CANDIDATE"))
                .withSubject(candidate.getId().toString())
                .sign(algorithm);

        return AuthCandidateResponseDTO.builder()
                .access_token(token)
                .expires_in(expiresIn.toEpochMilli())
                .build();
    }
}
