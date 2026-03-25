package com.doctor.dr.usercredential.controller;

import com.doctor.dr.usercredential.dto.request.UserLoginRequestDTO;
import com.doctor.dr.usercredential.dto.response.AuthenticatedUserCredentialResponseDTO;
import com.doctor.dr.usercredential.dto.response.UserCredentialResponseDTO;
import com.doctor.dr.usercredential.entity.UserCredential;
import com.doctor.dr.usercredential.service.UserCredentialService;
import jakarta.servlet.http.HttpServletResponse;
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
    public ResponseEntity<AuthenticatedUserCredentialResponseDTO> login(@RequestBody UserLoginRequestDTO userLoginRequestDTO, HttpServletResponse httpServletResponse) {
        AuthenticatedUserCredentialResponseDTO auth =this.userCredentialService.login(userLoginRequestDTO,httpServletResponse);
        return ResponseEntity.ok(auth);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<UserCredentialResponseDTO>> getAll() {
        List<UserCredentialResponseDTO> userCredentialResponseDTOS = this.userCredentialService.getAll();
        return ResponseEntity.ok(userCredentialResponseDTOS);
    }
}
