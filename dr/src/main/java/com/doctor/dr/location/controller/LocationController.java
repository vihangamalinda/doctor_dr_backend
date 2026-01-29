package com.doctor.dr.location.controller;

import com.doctor.dr.location.dto.LocationRequestDTO;
import com.doctor.dr.location.dto.LocationResponseDTO;
import com.doctor.dr.location.service.LocationService;
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

    @GetMapping("/all")
    public ResponseEntity<List<LocationResponseDTO>> getAll() {
        List<LocationResponseDTO> locationResponseDTOS = this.locationService.getAll();
        return ResponseEntity.ok(locationResponseDTOS);
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody LocationRequestDTO locationRequestDTO) {
        this.locationService.create(locationRequestDTO);
        return ResponseEntity.ok("created");
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<LocationResponseDTO> getById(@RequestParam("id") long id) {
        return ResponseEntity.ok(this.locationService.getById(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@RequestParam("id") long id) {
        this.locationService.deleteById(id);
        return ResponseEntity.ok("deleted");
    }

}
