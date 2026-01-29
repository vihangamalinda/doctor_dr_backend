package com.doctor.dr.hospital.controller;

import com.doctor.dr.hospital.dto.request.HospitalRequestDTO;
import com.doctor.dr.hospital.dto.response.HospitalResponseDTO;
import com.doctor.dr.hospital.service.HospitalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/hospital")
public class HospitalController {
    private final HospitalService hospitalService;
    public HospitalController(HospitalService hospitalService){
        this.hospitalService =hospitalService;
    }

    @GetMapping("all")
    public ResponseEntity<List<HospitalResponseDTO>> getAll(){
        return ResponseEntity.ok(this.hospitalService.getAll());
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<HospitalResponseDTO> getById(@RequestParam("id") long id){
        HospitalResponseDTO hospitalResponseDTO =this.hospitalService.getById(id);
        return ResponseEntity.ok(hospitalResponseDTO);
    }
    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody HospitalRequestDTO hospitalRequestDTO){
        this.hospitalService.create(hospitalRequestDTO);
        return ResponseEntity.ok("created");
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@RequestParam("id") long id){
        this.hospitalService.delete(id);
        return ResponseEntity.ok("deleted");
    }


}
