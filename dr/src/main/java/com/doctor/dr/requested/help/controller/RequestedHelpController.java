package com.doctor.dr.requested.help.controller;

import com.doctor.dr.requested.help.dto.request.RequestedHelpFeedbackRequestDTO;
import com.doctor.dr.requested.help.dto.request.RequestedHelpRequestDTO;
import com.doctor.dr.requested.help.dto.response.RequestedHelpResponseDTO;
import com.doctor.dr.requested.help.service.RequestedHelpService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/v1/requested-helper")
public class
RequestedHelpController {
    private final RequestedHelpService requestedHelpService;

    public RequestedHelpController(RequestedHelpService requestedHelpService) {
        this.requestedHelpService = requestedHelpService;
    }

    @Operation(summary = "Get all requested helps",
            description = "This endpoint retrieves a list of all requested helps in the system.",
            tags = {"Requested Help Management"})
    @GetMapping("/all")
    public ResponseEntity<List<RequestedHelpResponseDTO>> getAll() {
        List<RequestedHelpResponseDTO> requestedHelpResponseDTOS = this.requestedHelpService.getAll();
        return ResponseEntity.ok(requestedHelpResponseDTOS);
    }

    @Operation(summary = "Get requested help by ID",
            description = "This endpoint retrieves a requested help by its unique ID.",
            tags = {"Requested Help Management"})
    @GetMapping("/id/{id}")
    public ResponseEntity<RequestedHelpResponseDTO> getById(@PathVariable("id") long id) {
        RequestedHelpResponseDTO requestedHelpResponseDTOS = this.requestedHelpService.getById(id);
        return ResponseEntity.ok(requestedHelpResponseDTOS);
    }

    @Operation(summary = "Create a new requested help",
            description = "This endpoint allows the creation of a new requested help in the system.",
            tags = {"Requested Help Management"})
    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody RequestedHelpRequestDTO requestedHelpRequestDTO) {
        this.requestedHelpService.create(requestedHelpRequestDTO);
        return ResponseEntity.ok("created");
    }

    @Operation(summary = "Inactivate requested help by ID",
            description = "This endpoint inactivates an existing requested help identified by its unique ID.",
            tags = {"Requested Help Management"})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") long id) {
        this.requestedHelpService.deleteById(id);
        return ResponseEntity.ok("deleted");
    }

    @Operation(summary = "Get requested helps by created user profile ID",
            description = "This endpoint retrieves a list of requested helps created by a specific user profile ID.",
            tags = {"Requested Help Management"})
    @GetMapping("/created-user-profile/{id}")
    public ResponseEntity<List<RequestedHelpResponseDTO>> getRequestedHelpByCreatedUserProfileId(@PathVariable("id") long id) {
        List<RequestedHelpResponseDTO> requestedHelpResponseDTOS = this.requestedHelpService.getRequestedHelpByCreatedUserProfileId(id);
        return ResponseEntity.ok(requestedHelpResponseDTOS);
    }

    @Operation(summary = "Get requested helps by reviewed user profile ID",
            description = "This endpoint retrieves a list of requested helps reviewed by a specific user profile ID.",
            tags = {"Requested Help Management"})
    @GetMapping("/reviewed-user-profile/{id}")
    public ResponseEntity<List<RequestedHelpResponseDTO>> getRequestedHelpByReviewedUserProfileId(@PathVariable("id") long id) {
        List<RequestedHelpResponseDTO> requestedHelpResponseDTOS = this.requestedHelpService.getRequestedHelpByReviewedUserProfileId(id);
        return ResponseEntity.ok(requestedHelpResponseDTOS);
    }

    @Operation(summary = "Add feedback to a requested help",
            description = "This endpoint allows adding feedback to a specific requested help identified by its unique ID.",
            tags = {"Requested Help Management"})
    @PutMapping("/add-feed-back/{id}")
    public ResponseEntity<String> addFeedBack(@PathVariable("id") Long id, @RequestBody RequestedHelpFeedbackRequestDTO requestedHelpFeedbackRequestDTO){
        this.requestedHelpService.addFeedBack(id,requestedHelpFeedbackRequestDTO);
        return ResponseEntity.ok("Feedback added.");
    }

    @Operation(summary = "Get requested helps by status value",
            description = "This endpoint retrieves a list of requested helps based on their status value.",
            tags = {"Requested Help Management"})
    @GetMapping("/status-value/{value}")
    public ResponseEntity<List<RequestedHelpResponseDTO>> getRequestedHelpsByStatusValue(@PathVariable("value") long value ){
        List<RequestedHelpResponseDTO> helpList =this.requestedHelpService.getRequestedHelpsByStatusValue(value);
        return ResponseEntity.ok(helpList);
    }
}
