package com.doctor.dr.submission.controller;

import com.doctor.dr.submission.dto.request.SubmissionRequestDTO;
import com.doctor.dr.submission.dto.response.ModelPredictionPrimaryDTO;
import com.doctor.dr.submission.dto.response.SubmissionResponseDTO;
import com.doctor.dr.submission.service.SubmissionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/submission")
public class SubmissionController {
    private final SubmissionService submissionService;

    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @Operation(summary = "Get all submissions",
            description = "This endpoint retrieves a list of all submissions in the system.",
            tags = {"Submission Management"})
    @GetMapping("/all")
    public ResponseEntity<List<SubmissionResponseDTO>> getAll() {
        List<SubmissionResponseDTO> submissionList = this.submissionService.getAll();
        return ResponseEntity.ok(submissionList);
    }

    @Operation(summary = "Get submission by ID",
            description = "This endpoint retrieves a submission by its unique ID.",
            tags = {"Submission Management"})
    @GetMapping("/id/{id}")
    public ResponseEntity<SubmissionResponseDTO> getSubmissionById(@PathVariable("id") long id) {
        SubmissionResponseDTO submissionResponseDTO = this.submissionService.getSubmissionById(id);
        return ResponseEntity.ok(submissionResponseDTO);
    }

    @Operation(summary = "Create a new submission",
            description = "This endpoint allows the creation of a new submission in the system while diagnosing the patient's eye image.",
            tags = {"Submission Management"})
    @PostMapping(value="/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ModelPredictionPrimaryDTO> create(@ModelAttribute SubmissionRequestDTO submissionRequestDTO) {
        ModelPredictionPrimaryDTO result =this.submissionService.create(submissionRequestDTO);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Delete submission by ID",
            description = "This endpoint deletes a submission by its unique ID.",
            tags = {"Submission Management"})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") long id) {
        this.submissionService.deleteById(id);
        return ResponseEntity.ok("deleted");
    }

    @Operation(summary = "Get submissions by disease stage ID",
            description = "This endpoint retrieves a list of submissions associated with a specific disease stage ID.",
            tags = {"Submission Management"})
    @GetMapping("/disease-stage/{id}")
    public ResponseEntity<List<SubmissionResponseDTO>> getSubmissionByDiseaseStageId(@PathVariable("id") long id) {
        List<SubmissionResponseDTO> submissionResponseDTOS = this.submissionService.getSubmissionByDiseaseStageId(id);
        return ResponseEntity.ok(submissionResponseDTOS);
    }

    @Operation(summary = "Get submissions by user profile ID",
            description = "This endpoint retrieves a list of submissions associated with a specific user profile ID.",
            tags = {"Submission Management"})
    @GetMapping("/user-profile/{id}")
    public ResponseEntity<List<SubmissionResponseDTO>> getSubmissionsByUserProfileId(@PathVariable("id") long id){
        List<SubmissionResponseDTO> submissionResponseDTOList =this.submissionService.getSubmissionsByUserProfileId(id);
        return ResponseEntity.ok(submissionResponseDTOList);
    }
}
