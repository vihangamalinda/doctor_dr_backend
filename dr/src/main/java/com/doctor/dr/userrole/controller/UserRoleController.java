package com.doctor.dr.userrole.controller;

import com.doctor.dr.userrole.dto.request.UserRoleRequestDTO;
import com.doctor.dr.userrole.dto.response.UserRoleResponseDTO;
import com.doctor.dr.userrole.service.UserRoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/user-role")
public class UserRoleController {
    private final UserRoleService userRoleService;

    public UserRoleController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserRoleResponseDTO>> getAll(){
        List<UserRoleResponseDTO> userRoleResponseDTOS = this.userRoleService.getAll();
        return ResponseEntity.ok(userRoleResponseDTOS);
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody UserRoleRequestDTO userRoleRequestDTO){
        this.userRoleService.create(userRoleRequestDTO);
        return ResponseEntity.ok("created");
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserRoleResponseDTO> getById(@PathVariable("id") long id){
        UserRoleResponseDTO userRoleResponseDTO = this.userRoleService.getById(id);
        return ResponseEntity.ok(userRoleResponseDTO);
    }

    public ResponseEntity<String> deleteById(@PathVariable("id") long id){
        this.userRoleService.deleteById(id);
        return ResponseEntity.ok("deleted");
    }

}
