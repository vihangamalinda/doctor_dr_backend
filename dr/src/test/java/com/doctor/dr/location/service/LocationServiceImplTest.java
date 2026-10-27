package com.doctor.dr.location.service;

import com.doctor.dr.exception.ResourceNotFoundException;
import com.doctor.dr.location.dto.LocationRequestDTO;
import com.doctor.dr.location.dto.LocationResponseDTO;
import com.doctor.dr.location.entity.Location;
import com.doctor.dr.location.mapper.LocationMapper;
import com.doctor.dr.location.repository.LocationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocationServiceImplTest {
    @Mock
    private LocationRepository locationRepository;
    @Mock
    private LocationMapper locationMapper;

    @InjectMocks
    private LocationServiceImpl locationService;

    @Test
    void getAll_shouldReturnEmptyList_whenNoLocationExist() {
        when(this.locationRepository.findAllByIsActiveTrue()).thenReturn(Collections.emptyList());

        List<LocationResponseDTO> result = this.locationService.getAll();

        assertEquals(0, result.size());
        verify(this.locationRepository, times(1)).findAllByIsActiveTrue();
        verify(this.locationMapper, never()).toLocationResponseDTO(any(Location.class));
    }

    @Test
    void getAll_shouldReturnLocations_whenLocationExist() {
        Location location = mock(Location.class);
        LocationResponseDTO responseDTO = mock(LocationResponseDTO.class);

        when(this.locationRepository.findAllByIsActiveTrue()).thenReturn(List.of(location));
        when(this.locationMapper.toLocationResponseDTO(any(Location.class))).thenReturn(responseDTO);

        List<LocationResponseDTO> result = this.locationService.getAll();

        assertEquals(1, result.size());
        verify(this.locationRepository, times(1)).findAllByIsActiveTrue();
        verify(this.locationMapper, times(1)).toLocationResponseDTO(any(Location.class));
    }

    @Test
    void getById_shouldThrowResourceNotFoundException_whenInvalidIdIsGiven() {
        when(this.locationRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> this.locationService.getById(43L));

        assertEquals("Id not found.", exception.getMessage());
        verify(this.locationRepository, times(1)).findById(any(Long.class));
        verify(this.locationMapper, never()).toLocationResponseDTO(any(Location.class));
    }

    @Test
    void getById_shouldReturnLocation_whenValidIdIsGiven() {
        Location location = mock(Location.class);
        LocationResponseDTO responseDTO = mock(LocationResponseDTO.class);

        when(this.locationRepository.findById(anyLong())).thenReturn(Optional.of(location));
        when(this.locationMapper.toLocationResponseDTO(any(Location.class))).thenReturn(responseDTO);

        LocationResponseDTO result = this.locationService.getById(34L);
        assertNotNull(result);
        verify(this.locationRepository, times(1)).findById(any(Long.class));
        verify(this.locationMapper, times(1)).toLocationResponseDTO(any(Location.class));
    }

    @Test
    void deleteById_shouldThrowResourceNotFoundException_whenInvalidIdIsGiven() {
        when(this.locationRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> this.locationService.deleteById(413L));

        assertEquals("Id not found.", exception.getMessage());
        verify(this.locationRepository, times(1)).findById(any(Long.class));
        verify(this.locationRepository, never()).save(any(Location.class));
    }

    @Test
    void deleteById_shouldIgnore_whenDeactivatedValidIdIsGiven() {
        Location location = mock(Location.class);
        when(location.getIsActive()).thenReturn(false);

        when(this.locationRepository.findById(anyLong())).thenReturn(Optional.of(location));

        this.locationService.deleteById(4342L);
        verify(this.locationRepository, times(1)).findById(any(Long.class));
        verify(this.locationRepository, never()).save(any(Location.class));
    }

    @Test
    void deleteById_shouldDeactivate_whenActiveValidIdIsGiven() {
        Location location = mock(Location.class);
        when(location.getIsActive()).thenReturn(true);

        when(this.locationRepository.findById(anyLong())).thenReturn(Optional.of(location));
        when(this.locationRepository.save(any(Location.class))).thenReturn(location);

        this.locationService.deleteById(4342L);
        verify(this.locationRepository, times(1)).findById(any(Long.class));
        verify(this.locationRepository, times(1)).save(any(Location.class));
    }

    @Test
    void create_shouldCreate_whenValidInformationIsGiven() {
        LocationRequestDTO requestDTO = new LocationRequestDTO("index_location", "Road", "City", "Country", "postal_code");
        Location location = mock(Location.class);

        when(this.locationMapper.toLocation(any(LocationRequestDTO.class))).thenReturn(location);

        this.locationService.create(requestDTO);

        verify(this.locationMapper, times(1)).toLocation(any(LocationRequestDTO.class));
        verify(this.locationRepository, times(1)).save(any(Location.class));
    }
}