package com.brendosilva.gestao_vagas.modules.company.usecases;

import com.brendosilva.gestao_vagas.exceptions.UserFoundException;
import com.brendosilva.gestao_vagas.modules.company.entities.CompanyEntity;
import com.brendosilva.gestao_vagas.modules.company.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCompanyUseCase {

    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;

    public CompanyEntity execute(CompanyEntity entity) {
        this.companyRepository.findByUsernameOrEmail(entity.getUsername(), entity.getEmail())
                .ifPresent((user) -> {
                    throw new UserFoundException("Company already exists");
                });
        var password = passwordEncoder.encode(entity.getPassword());
        entity.setPassword(password);

        return this.companyRepository.save(entity);

    }
}
