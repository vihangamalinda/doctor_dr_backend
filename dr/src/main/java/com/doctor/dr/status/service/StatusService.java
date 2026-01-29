package com.doctor.dr.status.service;

import com.doctor.dr.status.dto.request.StatusRequestDTO;
import com.doctor.dr.status.dto.response.StatusResponseDTO;

import java.util.List;

public interface StatusService {
    List<StatusResponseDTO> getAll();

    StatusResponseDTO getById(long id);

    void create(StatusRequestDTO statusRequestDTO);

    void deleteById(long id);
}

