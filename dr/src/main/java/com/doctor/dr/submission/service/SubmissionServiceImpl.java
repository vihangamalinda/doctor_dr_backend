package com.doctor.dr.submission.service;

import com.doctor.dr.disease.stage.entity.DiseaseStage;
import com.doctor.dr.disease.stage.service.information.DiseaseStageInformation;
import com.doctor.dr.status.entity.Status;
import com.doctor.dr.status.service.information.StatusInformation;
import com.doctor.dr.submission.dto.request.SubmissionRequestDTO;
import com.doctor.dr.submission.dto.response.SubmissionResponseDTO;
import com.doctor.dr.submission.entity.Submission;
import com.doctor.dr.submission.mapper.SubmissionMapper;
import com.doctor.dr.submission.repository.SubmissionRepository;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubmissionServiceImpl implements SubmissionService {
    private final SubmissionRepository submissionRepository;
    private final SubmissionMapper submissionMapper;
    private final StatusInformation statusInformation;
    private final DiseaseStageInformation diseaseStageInformation;

    public SubmissionServiceImpl(SubmissionRepository submissionRepository, SubmissionMapper submissionMapper, StatusInformation statusInformation, DiseaseStageInformation diseaseStageInformation) {
        this.submissionRepository = submissionRepository;
        this.submissionMapper = submissionMapper;
        this.statusInformation = statusInformation;
        this.diseaseStageInformation = diseaseStageInformation;
    }

    @Override
    public List<SubmissionResponseDTO> getAll() {
        List<Submission> submissionList = this.submissionRepository.findAllByIsActiveTrue();
        return submissionList.stream().map(this.submissionMapper::toSubmissionResponseDTO).collect(Collectors.toList());
    }

    @Override
    public SubmissionResponseDTO getSubmissionById(long id) {
        Submission submission = findSubmissionById(id);
        return this.submissionMapper.toSubmissionResponseDTO(submission);
    }

    private Submission findSubmissionById(long id) {
        return this.submissionRepository.findById(id).orElseThrow(() -> new RuntimeException("Id not found."));
    }

    /**
     * TODO
     */
    @Override
    public void create(SubmissionRequestDTO submissionRequestDTO) {

        MultipartFile multipartFileImage = submissionRequestDTO.getMultipartFileImage();
        Submission submission = this.submissionMapper.toSubmission(submissionRequestDTO);
        submission.setIsActive(true);
        Status pendingStatus = getStatusInformationById(1);
        submission.setStatus(pendingStatus);
        persistEntity(submission);
//        submission.setStatus(); // pending
//        persistEntity(submission); before sending to model after reciving feed back persist again
        // need to use  model and verify the desease
        // submission.hasDisease =
        // submission.setDiseaseStage(getDiseaseStageByDiseaseLevel(level))

        DiseaseStage diseaseStage =getRandomDisease();
        if(diseaseStage.getId() !=1){
            submission.setHasDisease(true);
        }
        submission.setDiseaseStage(diseaseStage);
        Status sucseededStatus = getStatusInformationById(2);
        submission.setStatus(sucseededStatus);
        persistEntity(submission);
    }

    private Status getStatusInformationById(long statusId) {
        return this.statusInformation.getStatusById(statusId);
    }
    private DiseaseStage getDiseaseStageInformation(long id){
        return this.diseaseStageInformation.getDiseaseStageById(id);
    }
    private DiseaseStage getRandomDisease(){
      int value=  (int)(Math.random() * ((6 - 1) + 1)) + 1;
      return getDiseaseStageInformation(value);
    }

    private void persistEntity(Submission submission) {
        this.submissionRepository.save(submission);
    }

    @Override
    public void deleteById(long id) {
        Submission submission = this.findSubmissionById(id);
        if (submission.getIsActive()) {
            submission.setIsActive(false);
            persistEntity(submission);
        }
    }

    @Override
    public List<SubmissionResponseDTO> getSubmissionByDiseaseStageId(long id) {
        List<Submission> submissionList = this.submissionRepository.findByDiseaseStage_id(id);

        return submissionList.stream().map(this.submissionMapper::toSubmissionResponseDTO).collect(Collectors.toList());
    }

    @Override
    public List<SubmissionResponseDTO> getSubmissionsByUserProfileId(long userProfileId) {
        List<Submission> submissionList =this.submissionRepository.findByUserProfile_id(userProfileId);
        return submissionList.stream().map(this.submissionMapper::toSubmissionResponseDTO).collect(Collectors.toList());
    }

    private DiseaseStage getDiseaseStageByDiseaseLevel(int diseaseLevel) {
        DiseaseStage diseaseStage = new DiseaseStage();
        diseaseStage.setId(diseaseLevel); //might need to use proxy to comminucate to the diseaseStage layer
        return diseaseStage;
    }
}
