package com.doctor.dr.status.controller;

import com.doctor.dr.status.dto.request.StatusRequestDTO;
import com.doctor.dr.status.dto.response.StatusResponseDTO;
import com.doctor.dr.status.service.StatusService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/status")
public class StatusController {
    private final StatusService statusService;

    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @Operation(summary = "Get all statuses",
            description = "This endpoint retrieves a list of all statuses in the system.",
            tags = {"Status Management"})
    @GetMapping("/all")
    public ResponseEntity<List<StatusResponseDTO>> getAll() {
        List<StatusResponseDTO> statusResponseDTOList = this.statusService.getAll();
        return ResponseEntity.ok(statusResponseDTOList);
    }

    @Operation(summary = "Get status by ID",
            description = "This endpoint retrieves a status by its unique ID.",
            tags = {"Status Management"})
    @GetMapping("/id/{id}")
    public ResponseEntity<StatusResponseDTO> getById(@PathVariable("id") long id) {
        StatusResponseDTO statusResponseDTO = this.statusService.getById(id);
        return ResponseEntity.ok(statusResponseDTO);
    }

    @Operation(summary = "Create a new status",
            description = "This endpoint allows the creation of a new status in the system.",
            tags = {"Status Management"})
    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody StatusRequestDTO statusRequestDTO) {
        this.statusService.create(statusRequestDTO);
        return ResponseEntity.ok("created");
    }

    @Operation(summary = "Inactivate status by ID",
            description = "This endpoint inactivates an existing status identified by its unique ID.",
            tags = {"Status Management"})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") long id) {
        this.statusService.deleteById(id);
        return ResponseEntity.ok("deleted");
    }
}
