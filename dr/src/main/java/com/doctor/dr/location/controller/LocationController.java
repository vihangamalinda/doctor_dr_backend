package com.doctor.dr.location.controller;

import com.doctor.dr.location.dto.LocationRequestDTO;
import com.doctor.dr.location.dto.LocationResponseDTO;
import com.doctor.dr.location.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/location")
public class LocationController {
    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @Operation(summary = "Get all locations",
            description = "This endpoint retrieves a list of all locations in the system.",
            tags = {"Location Management"})
    @GetMapping("/all")
    public ResponseEntity<List<LocationResponseDTO>> getAll() {
        List<LocationResponseDTO> locationResponseDTOS = this.locationService.getAll();
        return ResponseEntity.ok(locationResponseDTOS);
    }

    @Operation(summary = "Create a new location",
            description = "This endpoint allows the creation of a new location in the system.",
            tags = {"Location Management"})
    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody LocationRequestDTO locationRequestDTO) {
        this.locationService.create(locationRequestDTO);
        return ResponseEntity.ok("created");
    }

    @Operation(summary = "Get location by ID",
            description = "This endpoint retrieves a location by its unique ID.",
            tags = {"Location Management"})
    @GetMapping("/id/{id}")
    public ResponseEntity<LocationResponseDTO> getById(@PathVariable("id") long id) {
        return ResponseEntity.ok(this.locationService.getById(id));
    }

    @Operation(summary = "Inactivate location by ID",
            description = "This endpoint inactivates an existing location identified by its unique ID.",
            tags = {"Location Management"})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") long id) {
        this.locationService.deleteById(id);
        return ResponseEntity.ok("deleted");
    }

}
