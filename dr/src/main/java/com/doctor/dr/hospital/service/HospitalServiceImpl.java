package com.doctor.dr.hospital.service;

import com.doctor.dr.hospital.dto.request.HospitalRequestDTO;
import com.doctor.dr.hospital.dto.response.HospitalResponseDTO;
import com.doctor.dr.hospital.entity.Hospital;
import com.doctor.dr.hospital.mapper.HospitalMapper;
import com.doctor.dr.hospital.repository.HospitalRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HospitalServiceImpl implements HospitalService {
    private final HospitalRepository hospitalRepository;
    private final HospitalMapper hospitalMapper;

    public HospitalServiceImpl(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
        this.hospitalMapper = Mappers.getMapper(HospitalMapper.class);
    }

    @Override
    public List<HospitalResponseDTO> getAll() {
        List<Hospital> hospitalList = this.hospitalRepository.findAllByIsActiveTrue();
        return hospitalList.stream().map(this.hospitalMapper::toHospitalResponseDTO).collect(Collectors.toList());
    }

    @Override
    public HospitalResponseDTO getById(long id) {
        Hospital hospital = findById(id);
        return this.hospitalMapper.toHospitalResponseDTO(hospital);
    }

    private Hospital findById(long id) {
        return this.hospitalRepository.findById(id).orElseThrow(() -> new RuntimeException("Id not found"));
    }

    @Override
    public void create(HospitalRequestDTO hospitalRequestDTO) {
        Hospital hospital = this.hospitalMapper.toHospital(hospitalRequestDTO);
        hospital.setIsActive(true);
        persistEntity(hospital);
    }

    @Override
    public void delete(long id) {
        Hospital hospital = findById(id);
        hospital.setIsActive(false);
        persistEntity(hospital);
    }

    private void persistEntity(Hospital hospital) {
        this.hospitalRepository.save(hospital);
    }
}
