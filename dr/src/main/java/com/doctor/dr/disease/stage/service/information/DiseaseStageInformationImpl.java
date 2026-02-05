package com.doctor.dr.disease.stage.service.information;

import com.doctor.dr.disease.stage.entity.DiseaseStage;
import com.doctor.dr.disease.stage.repository.DiseaseStageRepository;
import org.springframework.stereotype.Service;

@Service
public class DiseaseStageInformationImpl implements DiseaseStageInformation{

    private final DiseaseStageRepository diseaseStageRepository;

    public DiseaseStageInformationImpl(DiseaseStageRepository diseaseStageRepository) {
        this.diseaseStageRepository = diseaseStageRepository;
    }

    @Override
    public DiseaseStage getDiseaseStageById(long id) {
        return this.diseaseStageRepository.findById(id).orElseThrow(()-> new RuntimeException("Id not found."));
    }
}
