package com.doctor.dr.user.profile.mapper;

import com.doctor.dr.hospital.entity.Hospital;
import com.doctor.dr.user.profile.dto.request.HospitalDetailRequestDTO;
import com.doctor.dr.user.profile.dto.response.HospitalDetailResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", implementationName = "UserProfileHospitalDetailMapperImpl")
public interface HospitalDetailMapper {
    @Mapping(source = "id",target = "id")
    Hospital toHospital(HospitalDetailRequestDTO hospitalDetailRequestDTO);
    @Mapping(source = "id",target = "id")
    HospitalDetailResponseDTO toHospitalDetailResponseDTO(Hospital hospital);
}
