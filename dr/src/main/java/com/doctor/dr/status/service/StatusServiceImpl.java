package com.doctor.dr.status.service;

import com.doctor.dr.status.dto.request.StatusRequestDTO;
import com.doctor.dr.status.dto.response.StatusResponseDTO;
import com.doctor.dr.status.entity.Status;
import com.doctor.dr.status.mapper.StatusMapper;
import com.doctor.dr.status.repository.StatusRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatusServiceImpl implements StatusService {
    private final StatusRepository statusRepository;
    private final StatusMapper statusMapper;

    public StatusServiceImpl(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
        this.statusMapper = Mappers.getMapper(StatusMapper.class);
    }

    @Override
    public List<StatusResponseDTO> getAll() {
        List<Status> statusList = this.statusRepository.findAllByIsActiveTrue();
        return statusList.stream().map(this.statusMapper::toStatusResponseDTO).collect(Collectors.toList());
    }

    @Override
    public StatusResponseDTO getById(long id) {
        Status status = findById(id);
        return this.statusMapper.toStatusResponseDTO(status);
    }

    @Override
    public void create(StatusRequestDTO statusRequestDTO) {
        Status status = this.statusMapper.toStatus(statusRequestDTO);
        status.setIsActive(true);
        persistEntity(status);
    }

    @Override
    public void deleteById(long id) {
        Status status = this.findById(id);
        status.setIsActive(false);
        persistEntity(status);
    }

    private void persistEntity(Status status) {
        this.statusRepository.save(status);
    }

    private Status findById(long id) {
        return this.statusRepository.findById(id).orElseThrow(() -> new RuntimeException("Id not found"));
    }

}
