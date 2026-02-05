package com.doctor.dr.disease.stage.service;

import com.doctor.dr.disease.stage.dto.DiseaseStageResponseDTO;
import com.doctor.dr.disease.stage.dto.DiseaseStageRequestDTO;
import com.doctor.dr.disease.stage.entity.DiseaseStage;
import com.doctor.dr.disease.stage.mapper.DiseaseStageMapper;
import com.doctor.dr.disease.stage.repository.DiseaseStageRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiseaseStageServiceImpl implements DiseaseStageService {
    private final DiseaseStageRepository diseaseStageRepository;

    private final DiseaseStageMapper diseaseStageMapper;

    public DiseaseStageServiceImpl(DiseaseStageRepository diseaseStageRepository) {
        this.diseaseStageRepository = diseaseStageRepository;
        this.diseaseStageMapper = Mappers.getMapper(DiseaseStageMapper.class);
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
        return this.diseaseStageRepository.findById(id).orElseThrow(() -> new RuntimeException("Id not found."));
    }

    @Override
    public void create(DiseaseStageRequestDTO diseaseStageRequestDTO) {
        DiseaseStage diseaseStage = this.diseaseStageMapper.toDiseaseStage(diseaseStageRequestDTO);
        diseaseStage.setIsActive(true);
        this.diseaseStageRepository.save(diseaseStage);
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
