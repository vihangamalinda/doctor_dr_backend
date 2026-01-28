package com.doctor.dr.location.service;

import com.doctor.dr.location.dto.LocationRequestDTO;
import com.doctor.dr.location.dto.LocationResponseDTO;
import com.doctor.dr.location.entity.Location;
import com.doctor.dr.location.mapper.LocationMapper;
import com.doctor.dr.location.repository.LocationRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService{

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    public LocationServiceImpl(LocationRepository locationRepository){

        this.locationRepository =locationRepository;
        this.locationMapper = Mappers.getMapper(LocationMapper.class);
    }
    @Override
    public List<LocationResponseDTO> getAll() {
        List<Location> locationList = this.locationRepository.findAllByIsActiveTrue();
        return locationList.stream().map(this.locationMapper::toLocationResponseDTO).collect(Collectors.toList());
    }

    @Override
    public void create(LocationRequestDTO locationRequestDTO) {
        Location location = this.locationMapper.toLocation(locationRequestDTO);
        location.setActive(true);
        this.locationRepository.save(location);
    }

    @Override
    public LocationResponseDTO getById(long id) {
        Location location = findById(id);

        return this.locationMapper.toLocationResponseDTO(location);
    }

    private Location findById(long id) {
        return this.locationRepository.findById(id).orElseThrow(() -> new RuntimeException("Id not found."));
    }

    @Override
    public void deleteById(long id) {
        Location location =this.findById(id);
        location.setActive(false);
        this.locationRepository.save(location);
    }
}
