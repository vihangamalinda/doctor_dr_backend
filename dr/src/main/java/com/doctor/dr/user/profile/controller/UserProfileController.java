package com.doctor.dr.user.profile.controller;

import com.doctor.dr.user.profile.dto.request.UserProfileRequestDTO;
import com.doctor.dr.user.profile.dto.response.UserProfileResponseDTO;
import com.doctor.dr.user.profile.service.UserProfileService;
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

    @GetMapping("/all")
    public ResponseEntity<List<UserProfileResponseDTO>> getAll(){
        List<UserProfileResponseDTO> userProfileResponseDTOList =this.userProfileService.getAll();
        return ResponseEntity.ok(userProfileResponseDTOList);
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody UserProfileRequestDTO userProfileRequestDTO){
        this.userProfileService.create(userProfileRequestDTO);
        return ResponseEntity.ok("created");
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserProfileResponseDTO> getById(@PathVariable("id") long id){
           UserProfileResponseDTO userProfileResponseDTO =this.userProfileService.getById(id);
            return ResponseEntity.ok(userProfileResponseDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") long id){
        this.userProfileService.deleteById(id);
        return ResponseEntity.ok("deleted");
    }
}
