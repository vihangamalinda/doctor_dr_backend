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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserProfileServiceImplTest {
    @Mock
    private UserProfileRepository userProfileRepository;
    @Mock
    private UserProfileMapper userProfileMapper;
    @Mock
    private UserCredentialDetailService userCredentialDetailService;

    @InjectMocks
    private UserProfileServiceImpl userProfileService;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void getAll_shouldReturnEmptyList_whenNoActiveUserProfileExist() {
        when(this.userProfileRepository.findAllByIsActiveTrue()).thenReturn(Collections.emptyList());

        List<UserProfileResponseDTO> result = this.userProfileService.getAll();

        assertEquals(0, result.size());
        verify(this.userProfileRepository, times(1)).findAllByIsActiveTrue();
        verify(this.userProfileMapper, never()).toUserProfileResponseDTO(any(UserProfile.class));
    }

    @Test
    void getAll_shouldReturnUserProfileResponses_whenActiveUserProfileExist() {
        UserProfile userProfile = mock(UserProfile.class);
        UserProfileResponseDTO userProfileResponseDTO = mock(UserProfileResponseDTO.class);
        when(this.userProfileRepository.findAllByIsActiveTrue()).thenReturn(List.of(userProfile));
        when(this.userProfileMapper.toUserProfileResponseDTO(any())).thenReturn(userProfileResponseDTO);

        List<UserProfileResponseDTO> result = this.userProfileService.getAll();

        assertEquals(1, result.size());
        verify(this.userProfileRepository, times(1)).findAllByIsActiveTrue();
        verify(this.userProfileMapper, times(1)).toUserProfileResponseDTO(any(UserProfile.class));
    }

    @Test
    void getById_shouldThrowResourceNotFoundException_whenInvalidIdIsGiven() {
        when(this.userProfileRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> this.userProfileService.getById(33L));

        assertEquals("Id not found", exception.getMessage());
        verify(this.userProfileRepository, times(1)).findById(anyLong());
        verify(this.userProfileMapper, never()).toUserProfileResponseDTO(any(UserProfile.class));
    }


    @Test
    void getById_shouldReturnUserProfileResponse_whenValidIdIsGiven() {
        UserProfile userProfile = mock(UserProfile.class);
        UserProfileResponseDTO userProfileResponseDTO = mock(UserProfileResponseDTO.class);
        when(this.userProfileRepository.findById(anyLong())).thenReturn(Optional.ofNullable(userProfile));
        when(this.userProfileMapper.toUserProfileResponseDTO(any())).thenReturn(userProfileResponseDTO);

        UserProfileResponseDTO result = this.userProfileService.getById(45L);

        assertNotNull(result);
        verify(this.userProfileRepository, times(1)).findById(anyLong());
        verify(this.userProfileMapper, times(1)).toUserProfileResponseDTO(any(UserProfile.class));
    }

    @Test
    void getUserProfilesByUserRoleValue_shouldReturnsEmptyList_whenNoUserProfilesExistByGivenUserRoleValue() {
        String roleValue = "ADMIN_TYPE";

        when(this.userProfileRepository.findByUserCredential_RoleAssignments_UserRole_ValueAndIsActiveTrue(anyString())).thenReturn(Collections.emptyList());

        List<UserProfileResponseDTO> result = this.userProfileService.getUserProfilesByUserRoleValue(roleValue);

        assertEquals(0, result.size());
        verify(this.userProfileRepository, times(1)).findByUserCredential_RoleAssignments_UserRole_ValueAndIsActiveTrue(anyString());
        verify(this.userProfileMapper, never()).toUserProfileResponseDTO(any(UserProfile.class));
    }

    @Test
    void getUserProfilesByUserRoleValue_shouldReturnsUserProfileResponse_whenUserProfilesExistByGivenUserRoleValue() {
        String roleValue = "ADMIN_TYPE";
        UserProfile userProfile = mock(UserProfile.class);
        UserProfileResponseDTO userProfileResponseDTO = mock(UserProfileResponseDTO.class);

        when(this.userProfileRepository.findByUserCredential_RoleAssignments_UserRole_ValueAndIsActiveTrue(anyString())).thenReturn(List.of(userProfile));
        when(this.userProfileMapper.toUserProfileResponseDTO(any())).thenReturn(userProfileResponseDTO);

        List<UserProfileResponseDTO> result = this.userProfileService.getUserProfilesByUserRoleValue(roleValue);

        assertEquals(1, result.size());
        verify(this.userProfileRepository, times(1)).findByUserCredential_RoleAssignments_UserRole_ValueAndIsActiveTrue(anyString());
        verify(this.userProfileMapper, times(1)).toUserProfileResponseDTO(any(UserProfile.class));
    }

    @Test
    void deleteById_shouldThrowResourceNotFoundException_whenInvalidUserProfileIdIsGiven() {
        when(this.userProfileRepository.findById(anyLong())).thenReturn(Optional.empty());
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> this.userProfileService.deleteById(55L));
        assertEquals("Id not found", exception.getMessage());
        verify(this.userProfileRepository, times(1)).findById(anyLong());
        verify(this.userProfileRepository, never()).save(any(UserProfile.class));
    }

    @Test
    void deleteById_shouldIgnore_whenDeactivatedUserProfileIdIsGiven() {
        UserProfile userProfile = mock(UserProfile.class);
        when(userProfile.getIsActive()).thenReturn(false);

        when(this.userProfileRepository.findById(anyLong())).thenReturn(Optional.of(userProfile));
        this.userProfileService.deleteById(5L);
        verify(this.userProfileRepository, times(1)).findById(anyLong());
        verify(this.userProfileRepository, never()).save(any(UserProfile.class));
    }

    @Test
    void deleteById_shouldDeactivateUserProfile_whenActiveUserProfileIdIsGiven() {
        UserProfile userProfile = mock(UserProfile.class);
        when(userProfile.getIsActive()).thenReturn(true);

        when(this.userProfileRepository.findById(anyLong())).thenReturn(Optional.of(userProfile));
        this.userProfileService.deleteById(5L);
        verify(this.userProfileRepository, times(1)).findById(any(Long.class));
        verify(this.userProfileRepository, times(1)).save(any(UserProfile.class));
    }

    @Test
    void updatePassword_shouldThrowAccessDeniedException_whenAuthenticationIsNull() {
        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(null);

        SecurityContextHolder.setContext(context);
        UpdateUserProfilePasswordDTO request = mock(UpdateUserProfilePasswordDTO.class);
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);

        Exception exception = assertThrows(AccessDeniedException.class, () -> this.userProfileService.updatePassword(request, httpServletResponse));
        assertEquals("Authenticated user could not be identified.", exception.getMessage());
        verify(this.userCredentialDetailService, never()).updatePassword(any(UserCredential.class), anyString(), anyString(), any(HttpServletResponse.class));
    }

    @Test
    void updatePassword_shouldThrowAccessDeniedException_whenAuthenticationPrincipleIsNotInstanceOfCustomUserDetail() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(null);

        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(context);
        UpdateUserProfilePasswordDTO request = mock(UpdateUserProfilePasswordDTO.class);
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);

        Exception exception = assertThrows(AccessDeniedException.class, () -> this.userProfileService.updatePassword(request, httpServletResponse));
        assertEquals("Authenticated user could not be identified.", exception.getMessage());
        verify(this.userCredentialDetailService, never()).updatePassword(any(UserCredential.class), anyString(), anyString(), any(HttpServletResponse.class));
    }

    @Test
    void updatePassword_shouldUpdatePassword_whenValid() {
        UserCredential userCredential = mock(UserCredential.class);

        CustomUserDetail customUserDetail = mock(CustomUserDetail.class);
        when(customUserDetail.getUserCredential()).thenReturn(userCredential);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(customUserDetail);

        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(context);
        UpdateUserProfilePasswordDTO request = mock(UpdateUserProfilePasswordDTO.class);
        when(request.getCurrentPassword()).thenReturn("CURRENT_PASSWORD");
        when(request.getNewPassword()).thenReturn("NEW_PASSWORD");

        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);

        this.userProfileService.updatePassword(request, httpServletResponse);

        verify(this.userCredentialDetailService, times(1)).updatePassword(any(UserCredential.class), anyString(), anyString(), any(HttpServletResponse.class));
    }

    @Test
    void create_shouldCreate_whenValidInformationIsGiven() {
        UserInternalSystemInformationRequestDTO informationRequestDTO = mock(UserInternalSystemInformationRequestDTO.class);
        UserProfileRequestDTO requestDTO = mock(UserProfileRequestDTO.class);
        when(requestDTO.getUserInternalSystemInformation()).thenReturn(informationRequestDTO);
        UserProfile userProfile = mock(UserProfile.class);

        when(this.userProfileMapper.toUserProfile(any(UserProfileRequestDTO.class))).thenReturn(userProfile);
        when(this.userProfileRepository.save(any(UserProfile.class))).thenReturn(userProfile);
        doNothing().when(this.userCredentialDetailService).createUserCredentials(any(UserCredentialRegistrationRequestDTO.class));

        this.userProfileService.create(requestDTO);

        verify(this.userProfileMapper, times(1)).toUserProfile(any(UserProfileRequestDTO.class));
        verify(this.userCredentialDetailService, times(1)).createUserCredentials(any(UserCredentialRegistrationRequestDTO.class));
    }

}