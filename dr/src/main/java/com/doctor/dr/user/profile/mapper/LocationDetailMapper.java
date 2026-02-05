package com.doctor.dr.user.profile.mapper;

import com.doctor.dr.user.profile.dto.request.LocationDetailRequestDTO;
import com.doctor.dr.user.profile.dto.response.LocationDetailResponseDTO;
import com.doctor.dr.location.entity.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Mapper(componentModel = "spring", implementationName = "UserProfileLocationDetailMapperImpl")
public interface LocationDetailMapper {
    @Named("toLocation")
    Location toLocation(LocationDetailRequestDTO locationRequestDTO);

    @Named("toLocationResponseDTO")
    LocationDetailResponseDTO toLocationResponseDTO(Location location);

}
