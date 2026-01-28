package com.doctor.dr.location.mapper;

import com.doctor.dr.disease.stage.entity.DiseaseStage;
import com.doctor.dr.location.dto.LocationRequestDTO;
import com.doctor.dr.location.dto.LocationResponseDTO;
import com.doctor.dr.location.entity.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    Location toLocation(LocationRequestDTO locationRequestDTO);
    @Mapping(source = ".",target = "address",qualifiedByName = "mapAddress")
    LocationResponseDTO toLocationResponseDTO(Location location);

    @Named("mapAddress")
    default String mapAddress(Location location){
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
