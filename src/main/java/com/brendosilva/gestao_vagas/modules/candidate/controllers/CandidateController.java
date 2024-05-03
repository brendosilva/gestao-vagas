package com.brendosilva.gestao_vagas.modules.candidate.controllers;

import com.brendosilva.gestao_vagas.exceptions.UserFoundException;
import com.brendosilva.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import com.brendosilva.gestao_vagas.modules.candidate.entities.CandidateEntity;
import com.brendosilva.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import com.brendosilva.gestao_vagas.modules.candidate.usecases.ApplyJobCandidateUseCase;
import com.brendosilva.gestao_vagas.modules.candidate.usecases.CreateCandidateUseCase;
import com.brendosilva.gestao_vagas.modules.candidate.usecases.ListAllJobsByFilterUserCase;
import com.brendosilva.gestao_vagas.modules.candidate.usecases.ProfileCandidateUseCase;
import com.brendosilva.gestao_vagas.modules.company.entities.JobEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/candidate")
@RequiredArgsConstructor
public class CandidateController {
    private final CreateCandidateUseCase createCandidateUseCase;
    private final ProfileCandidateUseCase profileCandidateUseCase;
    private final ListAllJobsByFilterUserCase listAllJobsByFilterUserCase;
    private final ApplyJobCandidateUseCase applyJobCandidateUseCase;
    @PostMapping("/")
    @Tag(name = "Candidate", description = "Informações do candidate")
    @Operation(summary = "Criação do usuario", description = "Método responsavel pela criação do usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(
                            implementation = ProfileCandidateResponseDTO.class
                    ))
            }),
            @ApiResponse(responseCode = "400", description = "candidate already exists")
    })
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
    @Tag(name = "Candidate", description = "Informações do candidate")
    @Operation(summary = "Perfil do usuário", description = "Método responsavel por retorna o perfil do usuário")
    @SecurityRequirement(name = "jwt_auth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(
                            implementation = ProfileCandidateResponseDTO.class
                    ))
            }),
            @ApiResponse(responseCode = "400", description = "user not found")
    })
    public ResponseEntity<Object> get(HttpServletRequest request){
        var idCandidate = request.getAttribute("candidate_id");
        try {
            var profile = this.profileCandidateUseCase.execute(UUID.fromString(idCandidate.toString()));
            return ResponseEntity.ok().body(profile);
        } catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/job")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Tag(name = "Candidate", description = "Informações do candidate")
    @Operation(summary = "Listagem de vagas disponiveis para o candidate", description = "Método responsavel por lista vagas, baseada pelo filtro")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            array = @ArraySchema(schema = @Schema(
                                    implementation = JobEntity.class
                            ))
                    )
            })
    })
    @SecurityRequirement(name = "jwt_auth")
    public List<JobEntity> getJobsByFilter(@RequestParam String filter) {
        return this.listAllJobsByFilterUserCase.execute(filter);

    }


    @PostMapping("/job/apply")
    @Tag(name = "Candidate", description = "Informações do candidate")
    @Operation(summary = "Inscrição do candidato a uma vaga", description = "Método responsavel por realizar inscrição do candidato a uma vaga")
    @PreAuthorize("hasRole('CANDIDATE')")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> applyJob(@RequestBody UUID idJob, HttpServletRequest request) {
        var idCandidate = request.getAttribute("candidate_id");
        try {
            var result = this.applyJobCandidateUseCase.execute(UUID.fromString(idCandidate.toString()), idJob);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
