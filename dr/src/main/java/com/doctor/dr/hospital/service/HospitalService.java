package com.doctor.dr.hospital.service;

import com.doctor.dr.hospital.dto.request.HospitalRequestDTO;
import com.doctor.dr.hospital.dto.response.HospitalResponseDTO;

import java.util.List;

public interface HospitalService {
    List<HospitalResponseDTO> getAll();

    HospitalResponseDTO getById(long id);

    void create(HospitalRequestDTO hospitalRequestDTO);

    void delete(long id);
}
