package com.doctor.dr.disease.stage.controller;

import com.doctor.dr.disease.stage.dto.DiseaseStageResponseDTO;
import com.doctor.dr.disease.stage.dto.DiseaseStageRequestDTO;
import com.doctor.dr.disease.stage.service.DiseaseStageService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/disease-stage")
public class DiseaseStageController {
    private final DiseaseStageService diseaseStageService;

    public DiseaseStageController(DiseaseStageService diseaseStageService) {
        this.diseaseStageService = diseaseStageService;
    }

    @Operation(summary = "Get all disease stages",
            description = "This endpoint retrieves a list of all disease stages in the system.",
            tags = {"Disease Stage Management"})
    @GetMapping("/all")
    public ResponseEntity<List<DiseaseStageResponseDTO>> getAll() {
        List<DiseaseStageResponseDTO> diseaseStageResponseDTOList = this.diseaseStageService.getAll();
        return ResponseEntity.ok(diseaseStageResponseDTOList);
    }

    @Operation(summary = "Get disease stage by ID",
            description = "This endpoint retrieves a disease stage by its unique ID.",
            tags = {"Disease Stage Management"})
    @GetMapping("/id/{id}")
    public ResponseEntity<DiseaseStageResponseDTO> getById(@PathVariable("id") long id) {
        DiseaseStageResponseDTO diseaseStageResponseDTO = this.diseaseStageService.getById(id);
        return ResponseEntity.ok(diseaseStageResponseDTO);
    }

    @Operation(summary = "Create a new disease stage",
            description = "This endpoint allows the creation of a new disease stage in the system.",
            tags = {"Disease Stage Management"})
    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody DiseaseStageRequestDTO diseaseStageRequestDTO) {
        this.diseaseStageService.create(diseaseStageRequestDTO);
        return ResponseEntity.ok("created");
    }

    @Operation(summary = "Inactivate disease stage by ID",
            description = "This endpoint inactivates an existing disease stage identified by its unique ID.",
            tags = {"Disease Stage Management"})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") long id) {
        this.diseaseStageService.deleteById(id);
        return ResponseEntity.ok("deleted");
    }
}
