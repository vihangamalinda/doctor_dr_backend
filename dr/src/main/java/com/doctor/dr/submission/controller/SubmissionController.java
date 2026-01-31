package com.doctor.dr.submission.controller;

import com.doctor.dr.submission.dto.request.SubmissionRequestDTO;
import com.doctor.dr.submission.dto.response.SubmissionResponseDTO;
import com.doctor.dr.submission.service.SubmissionService;
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

    @GetMapping("/all")
    public ResponseEntity<List<SubmissionResponseDTO>> getAll() {
        List<SubmissionResponseDTO> submissionList = this.submissionService.getAll();
        return ResponseEntity.ok(submissionList);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<SubmissionResponseDTO> getSubmissionById(@RequestParam("id") long id) {
        SubmissionResponseDTO submissionResponseDTO = this.submissionService.getSubmissionById(id);
        return ResponseEntity.ok(submissionResponseDTO);
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody SubmissionRequestDTO submissionRequestDTO) {
        this.submissionService.create(submissionRequestDTO);
        return ResponseEntity.ok("created");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@RequestParam("id") long id) {
        this.submissionService.deleteById(id);
        return ResponseEntity.ok("deleted");
    }

    @GetMapping("/disease-stage/{id}")
    public ResponseEntity<List<SubmissionResponseDTO>> getSubmissionByDiseaseStageId(@RequestParam("id") long id) {
        List<SubmissionResponseDTO> submissionResponseDTOS = this.submissionService.getSubmissionByDiseaseStageId(id);
        return ResponseEntity.ok(submissionResponseDTOS);
    }

    @GetMapping("/user-profile/{id}")
    public ResponseEntity<List<SubmissionResponseDTO>> getSubmissionsByUserProfileId(@RequestParam("id") long id){
        List<SubmissionResponseDTO> submissionResponseDTOList =this.submissionService.getSubmissionsByUserProfileId(id);
        return ResponseEntity.ok(submissionResponseDTOList);
    }
}
