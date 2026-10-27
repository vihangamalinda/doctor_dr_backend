package com.doctor.dr.disease.stage.service;

import com.doctor.dr.disease.stage.dto.DiseaseStageResponseDTO;
import com.doctor.dr.disease.stage.dto.DiseaseStageRequestDTO;
import com.doctor.dr.disease.stage.entity.DiseaseStage;
import com.doctor.dr.disease.stage.mapper.DiseaseStageMapper;
import com.doctor.dr.disease.stage.repository.DiseaseStageRepository;
import com.doctor.dr.exception.DataInvalidException;
import com.doctor.dr.exception.ResourceNotFoundException;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiseaseStageServiceImpl implements DiseaseStageService {
    private final DiseaseStageRepository diseaseStageRepository;

    private final DiseaseStageMapper diseaseStageMapper;

    public DiseaseStageServiceImpl(DiseaseStageRepository diseaseStageRepository,DiseaseStageMapper diseaseStageMapper) {
        this.diseaseStageRepository = diseaseStageRepository;
        this.diseaseStageMapper = diseaseStageMapper;
    }

    @Override
    public List<DiseaseStageResponseDTO> getAll() {
        List<DiseaseStage> diseaseStageList = this.diseaseStageRepository.findAllByIsActiveTrue();
        return diseaseStageList.stream().map(this.diseaseStageMapper::toDiseaseStageResponseDTO).collect(Collectors.toList());
    }

    @Override
    public DiseaseStageResponseDTO getById(long id) {
        DiseaseStage diseaseStage = findById(id);
        return this.diseaseStageMapper.toDiseaseStageResponseDTO(diseaseStage);
    }

    private DiseaseStage findById(long id) {
        return this.diseaseStageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found."));
    }

    @Override
    public void create(DiseaseStageRequestDTO diseaseStageRequestDTO) {
        this.validate(diseaseStageRequestDTO);
        DiseaseStage diseaseStage = this.diseaseStageMapper.toDiseaseStage(diseaseStageRequestDTO);
        diseaseStage.setIsActive(true);
        this.diseaseStageRepository.save(diseaseStage);
    }

    private void validate(DiseaseStageRequestDTO dto) {
        if (dto.getDiseaseLevel() == null || dto.getName() == null || dto.getName().isBlank() || dto.getDescription() == null || dto.getDescription().isBlank()) {
            throw new DataInvalidException("valid input has not been provided");
        }
    }

    @Override
    public void deleteById(long id) {
        DiseaseStage diseaseStage = findById(id);
        if (diseaseStage.getIsActive()) {
            diseaseStage.setIsActive(false);
            this.diseaseStageRepository.save(diseaseStage);
        }
    }
}
