package com.brendosilva.gestao_vagas.modules.candidate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCandidateResponseDTO {
    @Schema(example = "desenvolvedor backend com foco em java e spring boot")
    private String description;
    @Schema(example = "bernadoabrel98")
    private String username;
    @Schema(example = "email@email.com")
    private String email;
    @Schema(example = "Bernado Abrel")
    private String name;
    private UUID id;
}
