package com.doctor.dr.usercredential.controller;

import com.doctor.dr.usercredential.dto.request.UserLoginRequestDTO;
import com.doctor.dr.usercredential.dto.response.UserCredentialResponseDTO;
import com.doctor.dr.usercredential.entity.UserCredential;
import com.doctor.dr.usercredential.service.UserCredentialService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/user-credential")
public class UserCredentialController {
    private final UserCredentialService userCredentialService;

    public UserCredentialController(UserCredentialService userCredentialService) {
        this.userCredentialService = userCredentialService;
    }

    @PostMapping("/login")
    public String login(@RequestBody UserLoginRequestDTO userLoginRequestDTO) {
        this.userCredentialService.login(userLoginRequestDTO);
        return "Test";
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<UserCredentialResponseDTO>> getAll() {
        List<UserCredentialResponseDTO> userCredentialResponseDTOS = this.userCredentialService.getAll();
        return ResponseEntity.ok(userCredentialResponseDTOS);
    }
}
