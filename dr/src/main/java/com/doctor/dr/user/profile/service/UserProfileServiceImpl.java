package com.doctor.dr.user.profile.service;

import com.doctor.dr.auth.custom.CustomUserDetail;
import com.doctor.dr.exception.ResourceNotFoundException;
import com.doctor.dr.user.profile.dto.request.UpdateUserProfilePasswordDTO;
import com.doctor.dr.user.profile.dto.request.UserInternalSystemInformationRequestDTO;
import com.doctor.dr.user.profile.dto.request.UserProfileRequestDTO;
import com.doctor.dr.user.profile.dto.response.UserProfileResponseDTO;
import com.doctor.dr.user.profile.entity.UserProfile;
import com.doctor.dr.user.profile.mapper.UserProfileMapper;
import com.doctor.dr.user.profile.repository.UserProfileRepository;
import com.doctor.dr.usercredential.dto.request.UserCredentialRegistrationRequestDTO;
import com.doctor.dr.usercredential.entity.UserCredential;
import com.doctor.dr.usercredential.service.information.UserCredentialDetailService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserProfileServiceImpl implements UserProfileService {
    private final UserProfileRepository userProfileRepository;

    private final UserProfileMapper userProfileMapper;
    private final UserCredentialDetailService userCredentialDetailService;

    public UserProfileServiceImpl(UserProfileRepository userProfileRepository, UserProfileMapper userProfileMapper, UserCredentialDetailService userCredentialDetailService) {
        this.userProfileRepository = userProfileRepository;
        this.userProfileMapper = userProfileMapper;
        this.userCredentialDetailService = userCredentialDetailService;
    }

    @Override
    public List<UserProfileResponseDTO> getAll() {
        List<UserProfile> userProfileList = this.userProfileRepository.findAllByIsActiveTrue();
        return userProfileList.stream().map(this.userProfileMapper::toUserProfileResponseDTO).collect(Collectors.toList());
    }

    @Override
    public void create(UserProfileRequestDTO userProfileRequestDTO) {
        UserProfile userProfile = this.userProfileMapper.toUserProfile(userProfileRequestDTO);
        userProfile.setIsActive(true);
        userProfile = persistEntity(userProfile);

        UserInternalSystemInformationRequestDTO userInternalDTO = userProfileRequestDTO.getUserInternalSystemInformation();
        UserCredentialRegistrationRequestDTO userCredentialDTO = new UserCredentialRegistrationRequestDTO(userInternalDTO.getUserName(), userProfile, userInternalDTO.getUserRoleId());
        userCredentialDetailService.createUserCredentials(userCredentialDTO);

    }


    @Override
    public UserProfileResponseDTO getById(long id) {
        UserProfile userProfile = findById(id);
        return this.userProfileMapper.toUserProfileResponseDTO(userProfile);
    }


    @Override
    public void deleteById(long id) {
        UserProfile userProfile = this.findById(id);
        userProfile.setIsActive(false);
        this.persistEntity(userProfile);
    }

    @Override
    public List<UserProfileResponseDTO> getUserProfilesByUserRoleValue(String userRoleValue) {
        List<UserProfile> userProfileList = this.userProfileRepository.findByUserCredential_RoleAssignments_UserRole_ValueAndIsActiveTrue(userRoleValue);
        List<UserProfile> userProfileList1 = this.userProfileRepository.findByUserCredential_RoleAssignments_UserRole_Value(userRoleValue);
        return userProfileList.stream().map(this.userProfileMapper::toUserProfileResponseDTO).collect(Collectors.toList());
    }

    @Override
    public void updatePassword(UpdateUserProfilePasswordDTO dto, HttpServletResponse response) {
        UserProfile userProfile = this.findById(dto.getUserProfileId());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null
                || !(authentication.getPrincipal() instanceof CustomUserDetail principal)) {
            throw new org.springframework.security.access.AccessDeniedException(
                    "Authenticated user could not be identified."
            );
        }
        UserCredential userCredential = principal.getUserCredential();

        this.userCredentialDetailService.updatePassword(userCredential, dto.getCurrentPassword(),dto.getNewPassword(),response);
    }

    private UserProfile findById(long id) {
        return this.userProfileRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found"));
    }


    private UserProfile persistEntity(UserProfile userProfile) {
        return this.userProfileRepository.save(userProfile);
    }
}
