package com.doctor.dr.submission.controller;

import com.doctor.dr.submission.dto.CreateSubmissionDTO;
import com.doctor.dr.submission.dto.SubmissionDTO;
import com.doctor.dr.submission.service.SubmissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/submission")
public class SubmissionController {
    private final SubmissionService submissionService;
    public SubmissionController(SubmissionService submissionService){
        this.submissionService =submissionService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<SubmissionDTO>> getAll(){
        List<SubmissionDTO> submissionList = this.submissionService.getAll();
        return  ResponseEntity.ok(submissionList);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<SubmissionDTO> getSubmissionById(@RequestParam("id") long id){
        SubmissionDTO submissionDTO = this.submissionService.getSubmissionById(id);
        return ResponseEntity.ok(submissionDTO);
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(CreateSubmissionDTO createSubmissionDTO){
        this.submissionService.create(createSubmissionDTO);
        return  ResponseEntity.ok("created");
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@RequestParam("id") long id){
        this.submissionService.deleteById(id);
        return ResponseEntity.ok("deleted");
    }
}
