package com.doctor.dr.status.controller;

import com.doctor.dr.status.dto.request.StatusRequestDTO;
import com.doctor.dr.status.dto.response.StatusResponseDTO;
import com.doctor.dr.status.service.StatusService;
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

    @GetMapping("/all")
    public ResponseEntity<List<StatusResponseDTO>> getAll() {
        List<StatusResponseDTO> statusResponseDTOList = this.statusService.getAll();
        return ResponseEntity.ok(statusResponseDTOList);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<StatusResponseDTO> getById(@RequestParam("id") long id) {
        StatusResponseDTO statusResponseDTO = this.statusService.getById(id);
        return ResponseEntity.ok(statusResponseDTO);
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody StatusRequestDTO statusRequestDTO) {
        this.statusService.create(statusRequestDTO);
        return ResponseEntity.ok("created");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@RequestParam("id") long id) {
        this.statusService.deleteById(id);
        return ResponseEntity.ok("deleted");
    }
}
