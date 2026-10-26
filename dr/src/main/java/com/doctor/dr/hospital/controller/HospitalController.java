package com.doctor.dr.hospital.controller;

import com.doctor.dr.hospital.dto.request.HospitalRequestDTO;
import com.doctor.dr.hospital.dto.response.HospitalResponseDTO;
import com.doctor.dr.hospital.service.HospitalService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/hospital")
public class HospitalController {
    private final HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @Operation(summary = "Get all hospitals",
            description = "This endpoint retrieves a list of all hospitals in the system.",
            tags = {"Hospital Management"})
    @GetMapping("all")
    public ResponseEntity<List<HospitalResponseDTO>> getAll() {
        return ResponseEntity.ok(this.hospitalService.getAll());
    }

    @Operation(summary = "Get hospital by ID",
            description = "This endpoint retrieves a hospital by its unique ID.",
            tags = {"Hospital Management"})
    @GetMapping("/id/{id}")
    public ResponseEntity<HospitalResponseDTO> getById(@PathVariable("id") long id) {
        HospitalResponseDTO hospitalResponseDTO = this.hospitalService.getById(id);
        return ResponseEntity.ok(hospitalResponseDTO);
    }

    @Operation(summary = "Create a new hospital",
            description = "This endpoint allows the creation of a new hospital in the system.",
            tags = {"Hospital Management"})
    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody HospitalRequestDTO hospitalRequestDTO) {
        this.hospitalService.create(hospitalRequestDTO);
        return ResponseEntity.ok("created");
    }

    @Operation(summary = "Inactivate hospital by ID",
            description = "This endpoint inactivates an existing hospital identified by its unique ID.",
            tags = {"Hospital Management"})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") long id) {
        this.hospitalService.deleteById(id);
        return ResponseEntity.ok("deleted");
    }

    @Operation(summary = "Get hospitals by internal system status",
            description = "This endpoint retrieves a list of hospitals based on their internal system status.",
            tags = {"Hospital Management"})
    @GetMapping("/system/{isInternalSystem}")
    public ResponseEntity<List<HospitalResponseDTO>> getHospitalsByIsInternalSystem(@PathVariable("isInternalSystem") boolean isInternalSystem){
      List<HospitalResponseDTO> hospitalResponseDTOS=  this.hospitalService.getHospitalsByIsInternalSystem(isInternalSystem);
      return ResponseEntity.ok(hospitalResponseDTOS);
    }


}
