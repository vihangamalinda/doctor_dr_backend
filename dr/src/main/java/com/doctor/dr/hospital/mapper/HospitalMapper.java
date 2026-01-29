package com.doctor.dr.hospital.mapper;

import com.doctor.dr.hospital.dto.request.HospitalRequestDTO;
import com.doctor.dr.hospital.dto.response.HospitalResponseDTO;
import com.doctor.dr.hospital.entity.Hospital;
import com.doctor.dr.location.dto.LocationRequestDTO;
import com.doctor.dr.location.dto.LocationResponseDTO;
import com.doctor.dr.location.entity.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper(componentModel = "spring", uses = {LocationDetailMapper.class})
public interface HospitalMapper {
    @Mapping(source = "location", target = "location", qualifiedByName = "toLocationResponseDTO")
    HospitalResponseDTO toHospitalResponseDTO(Hospital hospital);

    @Mapping(source = "location", target = "location", qualifiedByName = "toLocation")
    Hospital toHospital(HospitalRequestDTO hospitalRequestDTO);

}
