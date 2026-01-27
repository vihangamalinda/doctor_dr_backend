package com.doctor.dr.submission.service;

import com.doctor.dr.disease.stage.entity.DiseaseStage;
import com.doctor.dr.submission.dto.SubmissionRequestDTO;
import com.doctor.dr.submission.dto.SubmissionResponseDTO;
import com.doctor.dr.submission.entity.Submission;
import com.doctor.dr.submission.repository.SubmissionRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class SubmissionServiceImpl implements SubmissionService{
    private final SubmissionRepository submissionRepository;

    public SubmissionServiceImpl(SubmissionRepository submissionRepository) {
        this.submissionRepository = submissionRepository;
    }

    @Override
    public List<SubmissionResponseDTO> getAll() {
        List<Submission> submissionList =this.submissionRepository.findAllByIsActiveTrue();
        return submissionList.stream().map(this::createSubmissionDTO).collect(Collectors.toList());
    }

    @Override
    public SubmissionResponseDTO getSubmissionById(long id) {
        Submission submission = findSubmissionById(id);
        return this.createSubmissionDTO(submission);
    }

    private Submission findSubmissionById(long id) {
        return this.submissionRepository.findById(id).orElseThrow(() -> new RuntimeException("Id not found."));
    }

    /**
     *
     * TODO
     */
    @Override
    public void create(SubmissionRequestDTO submissionRequestDTO) {
        MultipartFile multipartFileImage = submissionRequestDTO.getMultipartFileImage();
        Submission submission = createSubmissionFromCreateSubmissionDTO(submissionRequestDTO);
        // need to use  model and verify the desease
        // submission.hasDisease =
        // submission.setDiseaseStage(getDiseaseStageByDiseaseLevel(level))
        this.submissionRepository.save(submission);
    }

    @Override
    public void deleteById(long id) {
        Submission submission = this.findSubmissionById(id);
        if (submission.isActive()) {
            submission.setActive(false);
            this.submissionRepository.save(submission);
        }
    }

    @Override
    public List<SubmissionResponseDTO> getSubmissionByDiseaseStageId(long id) {
        List<Submission> submissionList =this.submissionRepository.findByDiseaseStage_id(id);

        return submissionList.stream().map(this::createSubmissionDTO).collect(Collectors.toList());
    }

    private SubmissionResponseDTO createSubmissionDTO(Submission submission){
        return new SubmissionResponseDTO(submission);
    }
    private Submission createSubmissionFromCreateSubmissionDTO(SubmissionRequestDTO dto){
        return new Submission(dto.getSubmissionId(),dto.getPatientReferenceId(),dto.getCreatedDate(),dto.getCreatedTime(),dto.isActive(), dto.hasDisease(),null);
    }

    private DiseaseStage getDiseaseStageByDiseaseLevel(int diseaseLevel){
        DiseaseStage diseaseStage =new DiseaseStage();
        diseaseStage.setId(diseaseLevel); //might need to use proxy to comminucate to the diseaseStage layer
        return diseaseStage;
    }
}
