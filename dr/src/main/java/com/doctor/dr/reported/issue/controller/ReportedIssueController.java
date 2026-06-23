package com.doctor.dr.reported.issue.controller;

import com.doctor.dr.reported.issue.dto.request.ReportedIssueRequestDTO;
import com.doctor.dr.reported.issue.dto.response.ReportedIssueResponseDTO;
import com.doctor.dr.reported.issue.service.ReportedIssueService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Get all reported issues",
            description = "This endpoint retrieves a list of all reported issues in the system.",
            tags = {"Reported Issue Management"})
    @GetMapping("/all")
    public ResponseEntity<List<ReportedIssueResponseDTO>> getAll() {
        return ResponseEntity.ok(reportedIssueService.getAll());
    }

    @Operation(summary = "Get reported issue by ID",
            description = "This endpoint retrieves a reported issue by its unique ID.",
            tags = {"Reported Issue Management"})
    @GetMapping("/id/{id}")
    public ResponseEntity<ReportedIssueResponseDTO> getById(@PathVariable("id") long id) {
        ReportedIssueResponseDTO reportedIssueResponseDTO = reportedIssueService.getById(id);
        return ResponseEntity.ok(reportedIssueResponseDTO);
    }

    @Operation(summary = "Delete reported issue by ID",
            description = "This endpoint deletes a reported issue by its unique ID.",
            tags = {"Reported Issue Management"})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") long id) {
        reportedIssueService.deleteById(id);
        return ResponseEntity.ok("deleted");
    }

    @Operation(summary = "Create a new reported issue",
            description = "This endpoint allows the creation of a new reported issue in the system.",
            tags = {"Reported Issue Management"})
    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody ReportedIssueRequestDTO reportedIssueRequestDTO) {
        reportedIssueService.create(reportedIssueRequestDTO);
        return ResponseEntity.ok("created");
    }

    @Operation(summary = "Get reported issues by user profile ID",
            description = "This endpoint retrieves a list of reported issues associated with a specific user profile ID.",
            tags = {"Reported Issue Management"})
    @GetMapping("/user-profile/{id}")
    public ResponseEntity<List<ReportedIssueResponseDTO>> getReportedIssueByUserProfileId(@PathVariable("id") long id) {
        List<ReportedIssueResponseDTO> reportedIssueResponseDTOS = this.reportedIssueService.getReportedIssueByUserProfileId(id);
        return ResponseEntity.ok(reportedIssueResponseDTOS);
    }
}
