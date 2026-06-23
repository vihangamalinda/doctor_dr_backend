package com.doctor.dr.user.profile.controller;

import com.doctor.dr.user.profile.dto.request.UpdateUserProfilePasswordDTO;
import com.doctor.dr.user.profile.dto.request.UserProfileRequestDTO;
import com.doctor.dr.user.profile.dto.response.UserProfileResponseDTO;
import com.doctor.dr.user.profile.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/user-profile")
public class UserProfileController {
    private final UserProfileService userProfileService;

    public  UserProfileController(UserProfileService userProfileService){
        this.userProfileService = userProfileService;
    }

    @Operation(summary = "Get all user profiles",
            description = "This endpoint retrieves a list of all user profiles in the system.",
            tags = {"User Profile Management"})
    @GetMapping("/all")
    public ResponseEntity<List<UserProfileResponseDTO>> getAll(){
        List<UserProfileResponseDTO> userProfileResponseDTOList =this.userProfileService.getAll();
        return ResponseEntity.ok(userProfileResponseDTOList);
    }

    @Operation(summary = "Create a new user profile",
            description = "This endpoint allows the creation of a new user profile in the system.",
            tags = {"User Profile Management"})
    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody UserProfileRequestDTO userProfileRequestDTO){
        this.userProfileService.create(userProfileRequestDTO);
        return ResponseEntity.ok("created");
    }

    @Operation(summary = "Get user profile by ID",
            description = "This endpoint retrieves a user profile by its unique ID.",
            tags = {"User Profile Management"})
    @GetMapping("/id/{id}")
    public ResponseEntity<UserProfileResponseDTO> getById(@PathVariable("id") long id){
           UserProfileResponseDTO userProfileResponseDTO =this.userProfileService.getById(id);
            return ResponseEntity.ok(userProfileResponseDTO);
    }

    @Operation(summary = "Delete user profile by ID",
            description = "This endpoint deletes a user profile by its unique ID.",
            tags = {"User Profile Management"})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") long id){
        this.userProfileService.deleteById(id);
        return ResponseEntity.ok("deleted");
    }

    @Operation(summary = "Get user profiles by user role value",
            description = "This endpoint retrieves a list of user profiles associated with a specific user role value.",
            tags = {"User Profile Management"})
    @GetMapping("/user-role-value/{userRoleValue}")
    public ResponseEntity<List<UserProfileResponseDTO>> getUserProfilesByUserRoleValue(@PathVariable("userRoleValue") String userRoleValue){
        List<UserProfileResponseDTO> userProfileResponseDTOList =this.userProfileService.getUserProfilesByUserRoleValue(userRoleValue);
        return ResponseEntity.ok(userProfileResponseDTOList);
    }

    @Operation(summary = "Update user profile password",
            description = "This endpoint allows updating the password of a user profile.",
            tags = {"User Profile Management"})
    @PostMapping("/update-password")
    public ResponseEntity<String> updatePassword(@RequestBody UpdateUserProfilePasswordDTO updateUserProfilePasswordDTO, HttpServletResponse response){
        this.userProfileService.updatePassword(updateUserProfilePasswordDTO,response);
        return ResponseEntity.ok("password updated");
    }
}
