package com.brendosilva.gestao_vagas.modules.candidate.repositories;

import com.brendosilva.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ApplyJobRepository extends JpaRepository<ApplyJobEntity, UUID> {
}
