package com.doctor.dr.location.service;

import com.doctor.dr.location.dto.LocationRequestDTO;
import com.doctor.dr.location.dto.LocationResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LocationService {
    List<LocationResponseDTO> getAll();

    void create(LocationRequestDTO locationRequestDTO);

    LocationResponseDTO getById(long id);

    void deleteById(long id);
}
