package com.doctor.dr.user.profile.service.information;

import com.doctor.dr.exception.ResourceNotFoundException;
import com.doctor.dr.user.profile.entity.UserProfile;
import com.doctor.dr.user.profile.repository.UserProfileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserProfileDetailServiceImplTest {
    @Mock
    private UserProfileRepository userProfileRepository;
    @InjectMocks
    private UserProfileDetailServiceImpl userProfileDetailService;

    @Test
    void getUserProfileById_shouldThrow_whenInvalidUserProfileIdIsGiven() {

        when(this.userProfileRepository.findById(anyLong())).thenReturn(Optional.empty());
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> this.userProfileDetailService.getUserProfileById(43L));

        assertEquals("User Profile Id not found.", exception.getMessage());
        verify(this.userProfileRepository, times(1)).findById(any(Long.class));
    }

    @Test
    void getUserProfileById_shouldThrow_whenValidUserProfileIdIsGiven(){
        UserProfile userProfile =mock(UserProfile.class);
        when(this.userProfileRepository.findById(anyLong())).thenReturn(Optional.of(userProfile));
        UserProfile result =this.userProfileDetailService.getUserProfileById(43L);

        assertNotNull(result);
        verify(this.userProfileRepository, times(1)).findById(any(Long.class));
    }

}