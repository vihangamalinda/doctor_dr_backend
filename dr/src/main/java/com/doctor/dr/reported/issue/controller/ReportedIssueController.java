package com.doctor.dr.reported.issue.controller;

import com.doctor.dr.reported.issue.dto.request.ReportedIssueRequestDTO;
import com.doctor.dr.reported.issue.dto.response.ReportedIssueResponseDTO;
import com.doctor.dr.reported.issue.service.ReportedIssueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/v1/reported-issue")
public class ReportedIssueController {
    private final ReportedIssueService reportedIssueService;

    public ReportedIssueController(ReportedIssueService reportedIssueService) {
        this.reportedIssueService = reportedIssueService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ReportedIssueResponseDTO>> getAll() {
        return ResponseEntity.ok(reportedIssueService.getAll());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ReportedIssueResponseDTO> getById(@PathVariable("id") long id) {
        ReportedIssueResponseDTO reportedIssueResponseDTO = reportedIssueService.getById(id);
        return ResponseEntity.ok(reportedIssueResponseDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") long id) {
        reportedIssueService.deleteById(id);
        return ResponseEntity.ok("deleted");
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody ReportedIssueRequestDTO reportedIssueRequestDTO) {
        reportedIssueService.create(reportedIssueRequestDTO);
        return ResponseEntity.ok("created");
    }
}
