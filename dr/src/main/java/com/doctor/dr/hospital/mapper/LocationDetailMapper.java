package com.doctor.dr.hospital.mapper;


import com.doctor.dr.hospital.dto.request.LocationDetailRequestDTO;
import com.doctor.dr.hospital.dto.response.LocationDetailResponseDTO;
import com.doctor.dr.location.entity.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper(componentModel = "spring", implementationName = "HospitalLocationDetailMapperImpl")
public interface LocationDetailMapper {
    @Named("toLocation")
    Location toLocation(LocationDetailRequestDTO locationRequestDTO);

    @Named("toLocationResponseDTO")
    @Mapping(source = ".", target = "address", qualifiedByName = "mapAddress")
    LocationDetailResponseDTO toLocationResponseDTO(Location location);

    @Named("mapAddress")
    default String mapAddress(Location location) {
        if (location == null) return null;

        return Stream.of(
                        location.getLocationIndex(),
                        location.getRoad(),
                        location.getCity(),
                        location.getCountry()
                )
                .filter(Objects::nonNull)
                .collect(Collectors.joining("/"));
    }


}
