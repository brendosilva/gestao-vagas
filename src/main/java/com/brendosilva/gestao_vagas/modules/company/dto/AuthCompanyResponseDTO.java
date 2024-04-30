package com.brendosilva.gestao_vagas.modules.company.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthCompanyResponseDTO {
    private String access_token;
    private Long expires_in;
}
