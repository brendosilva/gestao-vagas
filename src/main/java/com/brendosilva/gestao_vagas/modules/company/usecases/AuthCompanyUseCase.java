package com.brendosilva.gestao_vagas.modules.company.usecases;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.brendosilva.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import com.brendosilva.gestao_vagas.modules.company.dto.AuthCompanyResponseDTO;
import com.brendosilva.gestao_vagas.modules.company.repositories.CompanyRepository;
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
public class AuthCompanyUseCase {
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;
    @Value("${security.token.secret}")
    private String secretKey;
    public AuthCompanyResponseDTO execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
        var expiresIn = Instant.now().plus(Duration.ofHours(2));
        var company = this.companyRepository.findByUsername(authCompanyDTO.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("username / password not found"));
        var matches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());
        if (!matches) {
            throw new AuthenticationException();
        }
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var token = JWT.create().withIssuer("javagas")
                .withClaim("roles", List.of("COMPANY"))
                .withSubject(company.getId().toString())
                .withExpiresAt(expiresIn)
                .sign(algorithm);

        return AuthCompanyResponseDTO.builder()
                .access_token(token)
                .expires_in(expiresIn.toEpochMilli())
                .build();

    }
}
