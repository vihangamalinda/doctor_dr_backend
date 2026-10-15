package com.doctor.dr.userrole.controller;

import com.doctor.dr.userrole.dto.request.UserRoleRequestDTO;
import com.doctor.dr.userrole.dto.response.UserRoleResponseDTO;
import com.doctor.dr.userrole.service.UserRoleService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/user-role")
public class UserRoleController {
    private final UserRoleService userRoleService;

    public UserRoleController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @Operation(summary = "Get all user roles",
            description = "This endpoint retrieves a list of all user roles in the system.",
            tags = {"User Role Management"})
    @GetMapping("/all")
    public ResponseEntity<List<UserRoleResponseDTO>> getAll(){
        List<UserRoleResponseDTO> userRoleResponseDTOS = this.userRoleService.getAll();
        return ResponseEntity.ok(userRoleResponseDTOS);
    }

    @Operation(summary = "Create a new user role",
            description = "This endpoint allows the creation of a new user role in the system.",
            tags = {"User Role Management"})
    @PreAuthorize("hasAuthority('ADMIN')") // Only users with 'ADMIN' authority can access this endpoint
    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody UserRoleRequestDTO userRoleRequestDTO){
        this.userRoleService.create(userRoleRequestDTO);
        return ResponseEntity.ok("created");
    }

    @Operation(summary = "Get user role by ID",
            description = "This endpoint retrieves a user role by its unique ID.",
            tags = {"User Role Management"})
    @GetMapping("/id/{id}")
    public ResponseEntity<UserRoleResponseDTO> getById(@PathVariable("id") long id){
        UserRoleResponseDTO userRoleResponseDTO = this.userRoleService.getById(id);
        return ResponseEntity.ok(userRoleResponseDTO);
    }

    @Operation(summary = "Inactivate user role by ID",
            description = "This endpoint inactivates an existing user role identified by its unique ID",
            tags = {"User Role Management"})
    @PreAuthorize("hasAuthority('ADMIN')") // Only users with 'ADMIN' authority can access this endpoint
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") long id){
        this.userRoleService.deleteById(id);
        return ResponseEntity.ok("deleted");
    }

    @Operation(summary = "Get user role by user role value",
            description = "This endpoint retrieves a user role by its unique user role value.",
            tags = {"User Role Management"})
    @GetMapping("/user-role-value/{userRoleValue}")
    public ResponseEntity<UserRoleResponseDTO> getUserRoleByUserRoleValue(@PathVariable("userRoleValue") String userRoleValue){
       UserRoleResponseDTO userRoleResponseDTO = this.userRoleService.getUserRoleByUserRoleValue(userRoleValue);
       return ResponseEntity.ok(userRoleResponseDTO);
    }
}
