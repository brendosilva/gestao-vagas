package com.brendosilva.gestao_vagas.modules.company.usecases;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.brendosilva.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import com.brendosilva.gestao_vagas.modules.company.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthCompanyUseCase {
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;
    @Value("${security.token.secret}")
    private String secretKey;
    public String execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
        var company = this.companyRepository.findByUsername(authCompanyDTO.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("username / password not found"));
        var matches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());
        if (!matches) {
            throw new AuthenticationException();
        }
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var token = JWT.create().withIssuer("javagas")
                .withSubject(company.getId().toString())
                .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
                .sign(algorithm);
        return token;

    }
}
