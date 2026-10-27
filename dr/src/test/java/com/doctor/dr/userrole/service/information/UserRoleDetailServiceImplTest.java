package com.doctor.dr.userrole.service.information;

import com.doctor.dr.exception.ResourceNotFoundException;
import com.doctor.dr.userrole.entity.UserRole;
import com.doctor.dr.userrole.repository.UserRoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRoleDetailServiceImplTest {

    @Mock
    private UserRoleRepository userRoleRepository;
    @InjectMocks
    private UserRoleDetailServiceImpl userRoleDetailService;


    @Test
    void getUserRoleById_shouldReturnUserRole_whenValidIdIsGiven() {
        UserRole userRole = mock(UserRole.class);

        when(this.userRoleRepository.findById(anyLong())).thenReturn(Optional.ofNullable(userRole));
        UserRole result = this.userRoleDetailService.getUserRoleById(3L);
        assertNotNull(result);
        verify(this.userRoleRepository, times(1)).findById(anyLong());
    }

    @Test
    void getUserRoleById_shouldThrowResourceNotFoundException_whenInvalidIdIsGiven() {
        when(this.userRoleRepository.findById(anyLong())).thenReturn(Optional.empty());
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> this.userRoleDetailService.getUserRoleById(3L));
        assertEquals("Given user role id was not found.", exception.getMessage());
    }
}