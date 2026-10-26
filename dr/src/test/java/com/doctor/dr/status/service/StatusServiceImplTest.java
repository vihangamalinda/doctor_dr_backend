package com.doctor.dr.status.service;

import com.doctor.dr.exception.ResourceNotFoundException;
import com.doctor.dr.status.dto.request.StatusRequestDTO;
import com.doctor.dr.status.dto.response.StatusResponseDTO;
import com.doctor.dr.status.entity.Status;
import com.doctor.dr.status.mapper.StatusMapper;
import com.doctor.dr.status.repository.StatusRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class StatusServiceImplTest {
    @Mock
    private StatusRepository statusRepository;
    @Mock
    private StatusMapper statusMapper;
    @InjectMocks
    private StatusServiceImpl statusService;

    @Test
    void getAll_shouldReturnEmptyList_whenNoStatusExist(){
        when(this.statusRepository.findAllByIsActiveTrue()).thenReturn(Collections.emptyList());

        List<StatusResponseDTO> result=this.statusService.getAll();

        assertEquals(0,result.size());
        verify(this.statusRepository,times(1)).findAllByIsActiveTrue();
        verify(this.statusMapper,never()).toStatusResponseDTO(any(Status.class));
    }

    @Test
    void getAll_shouldReturnStatus_whenStatusExist(){
        Status status =mock(Status.class);
        StatusResponseDTO responseDTO = mock(StatusResponseDTO.class);
        when(this.statusRepository.findAllByIsActiveTrue()).thenReturn(List.of(status));
        when(this.statusMapper.toStatusResponseDTO(any(Status.class))).thenReturn(responseDTO);

        List<StatusResponseDTO> result=this.statusService.getAll();

        assertEquals(1,result.size());
        verify(this.statusRepository,times(1)).findAllByIsActiveTrue();
        verify(this.statusMapper,times(1)).toStatusResponseDTO(any(Status.class));
    }

    @Test
    void getById_shouldThrownResourceNotFoundException_whenInvalidId(){
        when(this.statusRepository.findById(anyLong())).thenReturn(Optional.empty());
        Exception exception=assertThrows(ResourceNotFoundException.class,()->this.statusService.getById(43L));

        assertEquals("Id not found",exception.getMessage());
        verify(this.statusRepository,times(1)).findById(any(Long.class));
        verify(this.statusMapper,never()).toStatusResponseDTO(any(Status.class));
    }

    @Test
    void getById_shouldReturnStatus_whenValidId(){
        Status status = mock(Status.class);
        StatusResponseDTO responseDTO = mock(StatusResponseDTO.class);

        when(this.statusRepository.findById(anyLong())).thenReturn(Optional.of(status));
        when(this.statusMapper.toStatusResponseDTO(any(Status.class))).thenReturn(responseDTO);

        StatusResponseDTO result =this.statusService.getById(43L);

        assertNotNull(result);
        verify(this.statusRepository,times(1)).findById(any(Long.class));
        verify(this.statusMapper,times(1)).toStatusResponseDTO(any(Status.class));
    }

    @Test
    void create_shouldCreate_whenValidInformation(){
        StatusRequestDTO statusRequestDTO = new StatusRequestDTO("Status",4,"description","Status relate to table");
        Status status = mock(Status.class);

        when(this.statusMapper.toStatus(any(StatusRequestDTO.class))).thenReturn(status);

        this.statusService.create(statusRequestDTO);

        verify(this.statusMapper,times(1)).toStatus(any(StatusRequestDTO.class));
        verify(this.statusRepository,times(1)).save(any(Status.class));

    }

    @Test
    void deleteById_shouldThrowResourceNotFoundException_whenInvalidIdIsGiven() {
        when(this.statusRepository.findById(anyLong())).thenReturn(Optional.empty());
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> this.statusService.deleteById(45L));

        assertEquals("Id not found",exception.getMessage());
        verify(this.statusRepository,times(1)).findById(any(Long.class));
        verify(this.statusRepository,never()).save(any(Status.class));
    }

    @Test
    void deleteById_shouldIgnore_whenDeactivatedValidIdIsGiven(){
        Status status =mock(Status.class);
        when(status.getIsActive()).thenReturn(false);

        when(this.statusRepository.findById(anyLong())).thenReturn(Optional.of(status));

        this.statusService.deleteById(45L);

        verify(this.statusRepository,times(1)).findById(any(Long.class));
        verify(this.statusRepository,never()).save(any(Status.class));
    }

    @Test
    void deleteById_shouldDeactivate_whenActivatedValidIdIsGiven(){
        Status status =mock(Status.class);
        when(status.getIsActive()).thenReturn(true);

        when(this.statusRepository.findById(anyLong())).thenReturn(Optional.of(status));

        this.statusService.deleteById(45L);

        verify(this.statusRepository,times(1)).findById(any(Long.class));
        verify(this.statusRepository,times(1)).save(any(Status.class));
    }

}