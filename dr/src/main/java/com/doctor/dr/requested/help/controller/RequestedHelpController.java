package com.doctor.dr.requested.help.controller;

import com.doctor.dr.requested.help.dto.request.RequestedHelpRequestDTO;
import com.doctor.dr.requested.help.dto.response.RequestedHelpResponseDTO;
import com.doctor.dr.requested.help.service.RequestedHelpService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/v1/requested-helper")
public class RequestedHelpController {
    private final RequestedHelpService requestedHelpService;

    public RequestedHelpController(RequestedHelpService requestedHelpService) {
        this.requestedHelpService = requestedHelpService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<RequestedHelpResponseDTO>> getAll() {
        List<RequestedHelpResponseDTO> requestedHelpResponseDTOS = this.requestedHelpService.getAll();
        return ResponseEntity.ok(requestedHelpResponseDTOS);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<RequestedHelpResponseDTO> getById(@PathVariable("id") long id) {
        RequestedHelpResponseDTO requestedHelpResponseDTOS = this.requestedHelpService.getById(id);
        return ResponseEntity.ok(requestedHelpResponseDTOS);
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody RequestedHelpRequestDTO requestedHelpRequestDTO) {
        this.requestedHelpService.create(requestedHelpRequestDTO);
        return ResponseEntity.ok("created");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") long id) {
        this.requestedHelpService.deleteById(id);
        return ResponseEntity.ok("deleted");
    }

    @GetMapping("/created-user-profile/{id}")
    public ResponseEntity<List<RequestedHelpResponseDTO>> getRequestedHelpByCreatedUserProfileId(@PathVariable("id") long id) {
        List<RequestedHelpResponseDTO> requestedHelpResponseDTOS = this.requestedHelpService.getRequestedHelpByCreatedUserProfileId(id);
        return ResponseEntity.ok(requestedHelpResponseDTOS);
    }

    @GetMapping("/reviewed-user-profile/{id}")
    public ResponseEntity<List<RequestedHelpResponseDTO>> getRequestedHelpByReviewedUserProfileId(@PathVariable("id") long id) {
        List<RequestedHelpResponseDTO> requestedHelpResponseDTOS = this.requestedHelpService.getRequestedHelpByReviewedUserProfileId(id);
        return ResponseEntity.ok(requestedHelpResponseDTOS);
    }
}
