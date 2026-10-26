package com.doctor.dr.hospital.service;

import com.doctor.dr.exception.ResourceNotFoundException;
import com.doctor.dr.hospital.dto.request.HospitalRequestDTO;
import com.doctor.dr.hospital.dto.response.HospitalResponseDTO;
import com.doctor.dr.hospital.entity.Hospital;
import com.doctor.dr.hospital.mapper.HospitalMapper;
import com.doctor.dr.hospital.repository.HospitalRepository;
import jdk.jshell.spi.ExecutionControl;
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
class HospitalServiceImplTest {
    @Mock
    private HospitalRepository hospitalRepository;
    @Mock
    private HospitalMapper hospitalMapper;
    @InjectMocks
    private HospitalServiceImpl hospitalService;

    @Test
    void getAll_shouldReturnEmptyList_whenNoHospitalExist() {
        when(this.hospitalRepository.findAllByIsActiveTrue()).thenReturn(Collections.emptyList());

        List<HospitalResponseDTO> result = this.hospitalService.getAll();

        assertEquals(0, result.size());
        verify(this.hospitalRepository, times(1)).findAllByIsActiveTrue();
        verify(this.hospitalMapper, never()).toHospitalResponseDTO(any(Hospital.class));
    }

    @Test
    void getAll_shouldReturnHospitals_whenHospitalExist() {
        Hospital hospital = mock(Hospital.class);
        HospitalResponseDTO responseDTO = mock(HospitalResponseDTO.class);

        when(this.hospitalRepository.findAllByIsActiveTrue()).thenReturn(List.of(hospital));
        when(this.hospitalMapper.toHospitalResponseDTO(any(Hospital.class))).thenReturn(responseDTO);

        List<HospitalResponseDTO> result = this.hospitalService.getAll();
        assertEquals(1, result.size());
        verify(this.hospitalRepository, times(1)).findAllByIsActiveTrue();
        verify(this.hospitalMapper, times(1)).toHospitalResponseDTO(any(Hospital.class));
    }

    @Test
    void getById_shouldThrowResourceNotFoundException_whenInvalidIdIsGiven() {

        when(this.hospitalRepository.findById(anyLong())).thenReturn(Optional.empty());
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> this.hospitalService.getById(456L));

        assertEquals("Id not found", exception.getMessage());
        verify(this.hospitalRepository, times(1)).findById(any(Long.class));
        verify(this.hospitalMapper, never()).toHospitalResponseDTO(any(Hospital.class));
    }

    @Test
    void getById_shouldReturnHospital_whenValidIdIsGiven() {
        Hospital hospital = mock(Hospital.class);
        HospitalResponseDTO responseDTO = mock(HospitalResponseDTO.class);
        when(this.hospitalRepository.findById(anyLong())).thenReturn(Optional.of(hospital));
        when(this.hospitalMapper.toHospitalResponseDTO(any(Hospital.class))).thenReturn(responseDTO);

        HospitalResponseDTO result = this.hospitalService.getById(456L);

        assertNotNull(result);
        verify(this.hospitalRepository, times(1)).findById(any(Long.class));
        verify(this.hospitalMapper, times(1)).toHospitalResponseDTO(any(Hospital.class));
    }

    @Test
    void deleteById_shouldThrowResourceNotFoundException_whenInvalidIdIsGiven() {
        when(this.hospitalRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> this.hospitalService.deleteById(88L));
        verify(this.hospitalRepository, times(1)).findById(any(Long.class));
        verify(this.hospitalRepository, never()).save(any(Hospital.class));
    }

    @Test
    void deleteById_shouldIgnore_whenDeactivateValidIdIsGiven() {
        Hospital hospital = mock(Hospital.class);
        when(hospital.getIsActive()).thenReturn(false);

        when(this.hospitalRepository.findById(anyLong())).thenReturn(Optional.of(hospital));
        this.hospitalService.deleteById(88L);

        verify(this.hospitalRepository, times(1)).findById(any(Long.class));
        verify(this.hospitalRepository, never()).save(any(Hospital.class));
    }

    @Test
    void deleteById_shouldDeactivate_whenActivateValidIdIsGiven() {
        Hospital hospital = mock(Hospital.class);
        when(hospital.getIsActive()).thenReturn(true);

        when(this.hospitalRepository.findById(anyLong())).thenReturn(Optional.of(hospital));
        this.hospitalService.deleteById(88L);

        verify(this.hospitalRepository, times(1)).findById(any(Long.class));
        verify(this.hospitalRepository, times(1)).save(any(Hospital.class));
    }

    @Test
    void getHospitalsByIsInternalSystem_shouldReturnEmptyList_whenNoInternalSystemHospitalsExist() {
        when(this.hospitalRepository.findByIsInternalSystem(true)).thenReturn(Collections.emptyList());

        List<HospitalResponseDTO> result = this.hospitalService.getHospitalsByIsInternalSystem(true);

        assertEquals(0, result.size());
        verify(this.hospitalRepository, times(1)).findByIsInternalSystem(true);
        verify(this.hospitalMapper, never()).toHospitalResponseDTO(any(Hospital.class));
    }

    @Test
    void getHospitalsByIsInternalSystem_shouldReturnHospitals_whenInternalSystemHospitalsExist() {
        Hospital hospital = mock(Hospital.class);
        when(this.hospitalRepository.findByIsInternalSystem(true)).thenReturn(List.of(hospital));

        List<HospitalResponseDTO> result = this.hospitalService.getHospitalsByIsInternalSystem(true);

        assertEquals(1, result.size());
        verify(this.hospitalRepository, times(1)).findByIsInternalSystem(true);
        verify(this.hospitalMapper, times(1)).toHospitalResponseDTO(any(Hospital.class));
    }

    @Test
    void getHospitalsByIsInternalSystem_shouldReturnEmptyList_whenNoExternalSystemHospitalsExist() {
        when(this.hospitalRepository.findByIsInternalSystem(false)).thenReturn(Collections.emptyList());

        List<HospitalResponseDTO> result = this.hospitalService.getHospitalsByIsInternalSystem(false);

        assertEquals(0, result.size());
        verify(this.hospitalRepository, times(1)).findByIsInternalSystem(false);
        verify(this.hospitalMapper, never()).toHospitalResponseDTO(any(Hospital.class));
    }

    @Test
    void getHospitalsByIsInternalSystem_shouldReturnHospitals_whenExternalSystemHospitalsExist() {
        Hospital hospital = mock(Hospital.class);
        when(this.hospitalRepository.findByIsInternalSystem(false)).thenReturn(List.of(hospital));

        List<HospitalResponseDTO> result = this.hospitalService.getHospitalsByIsInternalSystem(false);

        assertEquals(1, result.size());
        verify(this.hospitalRepository, times(1)).findByIsInternalSystem(false);
        verify(this.hospitalMapper, times(1)).toHospitalResponseDTO(any(Hospital.class));
    }

    @Test
    void create_shouldCreateHospital_whenValidInformationIsGiven(){
        HospitalRequestDTO requestDTO =mock(HospitalRequestDTO.class);
        Hospital hospital = mock(Hospital.class);
        when(this.hospitalMapper.toHospital(any(HospitalRequestDTO.class))).thenReturn(hospital);
        when(this.hospitalRepository.save(hospital)).thenReturn(hospital);

        this.hospitalService.create(requestDTO);

        verify(this.hospitalMapper,times(1)).toHospital(any(HospitalRequestDTO.class));
        verify(this.hospitalRepository,times(1)).save(any(Hospital.class));
    }
}