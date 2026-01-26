package com.doctor.dr.disease.stage.service;

import com.doctor.dr.disease.stage.dto.DiseaseStageResponseDTO;
import com.doctor.dr.disease.stage.dto.DiseaseStageRequestDTO;
import com.doctor.dr.disease.stage.entity.DiseaseStage;
import com.doctor.dr.disease.stage.repository.DiseaseStageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiseaseStageServiceImpl implements DiseaseStageService {
    private final DiseaseStageRepository diseaseStageRepository;

    public DiseaseStageServiceImpl(DiseaseStageRepository diseaseStageRepository) {
        this.diseaseStageRepository = diseaseStageRepository;
    }

    @Override
    public List<DiseaseStageResponseDTO> getAll() {
        List<DiseaseStage> diseaseStageList = this.diseaseStageRepository.findAllByIsActiveTrue();
        return diseaseStageList.stream().map(this::createDiseaseStageDTO).collect(Collectors.toList());
    }

    @Override
    public DiseaseStageResponseDTO getById(long id) {
        DiseaseStage diseaseStage = findById(id);

        return this.createDiseaseStageDTO(diseaseStage);
    }

    private DiseaseStage findById(long id) {
        return this.diseaseStageRepository.findById(id).orElseThrow(() -> new RuntimeException("Id not found."));
    }

    @Override
    public void create(DiseaseStageRequestDTO diseaseStageRequestDTO) {
        DiseaseStage diseaseStage = this.createDiseaseStage(diseaseStageRequestDTO);
        this.diseaseStageRepository.save(diseaseStage);
    }

    @Override
    public void deleteById(long id) {
        DiseaseStage diseaseStage = findById(id);
        if (diseaseStage.isActive()) {
            diseaseStage.setActive(false);
            this.diseaseStageRepository.save(diseaseStage);
        }
    }

    private DiseaseStageResponseDTO createDiseaseStageDTO(DiseaseStage diseaseStage) {
        return new DiseaseStageResponseDTO(diseaseStage);
    }

    private DiseaseStage createDiseaseStage(DiseaseStageRequestDTO dto) {
//        List<Submission> submissionList=dto.getSubmissionDTOList().stream().map(SubmissionMapper::dtoToEntity).collect(Collectors.toList());
        return new DiseaseStage(dto.getId(), dto.getName(), dto.getDescription(), dto.isActive());
    }
}
