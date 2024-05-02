package com.brendosilva.gestao_vagas.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateJobDTO {
    @Schema(example = "Vagas para desenvolvedora Node/java/python", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;
    @Schema(example = "Vale transporte, vale refeição", requiredMode = Schema.RequiredMode.REQUIRED)
    private String benefits;
    @Schema(example = "JUNIOR/PLENO/SENIOR", requiredMode = Schema.RequiredMode.REQUIRED)
    private String level;
}
