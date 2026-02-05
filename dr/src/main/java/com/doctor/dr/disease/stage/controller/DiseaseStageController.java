package com.doctor.dr.disease.stage.controller;

import com.doctor.dr.disease.stage.dto.DiseaseStageResponseDTO;
import com.doctor.dr.disease.stage.dto.DiseaseStageRequestDTO;
import com.doctor.dr.disease.stage.service.DiseaseStageService;
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

    @GetMapping("/all")
    public ResponseEntity<List<DiseaseStageResponseDTO>> getAll() {
        List<DiseaseStageResponseDTO> diseaseStageResponseDTOList = this.diseaseStageService.getAll();
        return ResponseEntity.ok(diseaseStageResponseDTOList);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<DiseaseStageResponseDTO> getById(@PathVariable("id") long id) {
        DiseaseStageResponseDTO diseaseStageResponseDTO = this.diseaseStageService.getById(id);
        return ResponseEntity.ok(diseaseStageResponseDTO);
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody DiseaseStageRequestDTO diseaseStageRequestDTO) {
        this.diseaseStageService.create(diseaseStageRequestDTO);
        return ResponseEntity.ok("created");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") long id) {
        this.diseaseStageService.deleteById(id);
        return ResponseEntity.ok("deleted");
    }
}
