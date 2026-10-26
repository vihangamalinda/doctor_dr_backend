package com.doctor.dr.status.service.information;

import com.doctor.dr.exception.ResourceNotFoundException;
import com.doctor.dr.status.entity.Status;
import com.doctor.dr.status.repository.StatusRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatusInformationImplTest {
    @Mock
    private StatusRepository statusRepository;
    @InjectMocks
    private StatusInformationImpl statusInformation;

    @Test
    void getStatusById_shouldThrowResourceNotFoundException_whenNoStatusExistForGivenId() {
        when(this.statusRepository.findById(anyLong())).thenReturn(Optional.empty());
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> this.statusInformation.getStatusById(45L));

        assertEquals("Id not found.", exception.getMessage());
        verify(this.statusRepository, times(1)).findById(any(Long.class));
    }


    @Test
    void getStatusById_shouldStatus_whenStatusExistForGivenId() {
        Status status = mock(Status.class);
        when(this.statusRepository.findById(anyLong())).thenReturn(Optional.of(status));
        Status result = this.statusInformation.getStatusById(45L);

        assertNotNull(result);
        verify(this.statusRepository, times(1)).findById(any(Long.class));
    }

}