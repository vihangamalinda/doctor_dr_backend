package com.doctor.dr.submission.service;

import com.doctor.dr.disease.stage.entity.DiseaseStage;
import com.doctor.dr.disease.stage.service.information.DiseaseStageInformation;
import com.doctor.dr.externalapiservice.service.ModelClientService;
import com.doctor.dr.status.entity.Status;
import com.doctor.dr.status.service.information.StatusInformation;
import com.doctor.dr.submission.dto.external.ModelPredictionResponse;
import com.doctor.dr.submission.dto.external.PredictionDetailResponse;
import com.doctor.dr.submission.dto.response.*;
import com.doctor.dr.submission.constants.SubmissionStatusId;

import com.doctor.dr.submission.dto.request.SubmissionRequestDTO;
import com.doctor.dr.submission.entity.Submission;
import com.doctor.dr.submission.mapper.SubmissionMapper;
import com.doctor.dr.submission.repository.SubmissionRepository;
import com.doctor.dr.user.profile.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubmissionServiceImpl implements SubmissionService {
    private final SubmissionRepository submissionRepository;
    private final SubmissionMapper submissionMapper;
    private final StatusInformation statusInformation;
    private final DiseaseStageInformation diseaseStageInformation;
    private final ModelClientService modelClientService;

    public SubmissionServiceImpl(SubmissionRepository submissionRepository, SubmissionMapper submissionMapper, StatusInformation statusInformation, DiseaseStageInformation diseaseStageInformation, ModelClientService modelClientService) {
        this.submissionRepository = submissionRepository;
        this.submissionMapper = submissionMapper;
        this.statusInformation = statusInformation;
        this.diseaseStageInformation = diseaseStageInformation;
        this.modelClientService = modelClientService;
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
    public ModelPredictionPrimaryDTO create(SubmissionRequestDTO submissionRequestDTO) {



        Submission submission = this.submissionMapper.toSubmission(submissionRequestDTO);
        submission.setIsActive(true);
        Status pendingStatus = getStatusInformationById(SubmissionStatusId.IN_PROGRESS);
        submission.setStatus(pendingStatus);
        persistEntity(submission);
//        submission.setStatus(); // pending
//        persistEntity(submission); before sending to model after reciving feed back persist again
        // need to use  model and verify the desease
        // submission.hasDisease =
        // submission.setDiseaseStage(getDiseaseStageByDiseaseLevel(level))

        MultipartFile multipartFileImage = submissionRequestDTO.getMultipartFileImage();
        try {
            ModelPredictionResponse modelPrediction= modelClientService.performModelPrediction(multipartFileImage,ModelPredictionResponse.class);
            validateModelPrediction(modelPrediction);

            Integer predictionIndex = modelPrediction.getData().getPredictionIndex();
            DiseaseStage diseaseStage = getDiseaseStageInformationByDiseaseLevel(predictionIndex);
            boolean hasDisease = diseaseStage.getDiseaseLevel() != 0;
            submission.setHasDisease(hasDisease);


            submission.setDiseaseStage(diseaseStage);
            Status sucseededStatus = getStatusInformationById(SubmissionStatusId.COMPLETED);
            submission.setStatus(sucseededStatus);
            Submission persistedEntity=persistEntity(submission);
            return generateResponse(persistedEntity,modelPrediction,multipartFileImage);
        }catch (Exception e){
            Status failedStatus = getStatusInformationById(SubmissionStatusId.REJECTED);
            submission.setStatus(failedStatus);
            throw  new RuntimeException("Model prediction failed: " + e.getMessage());
        }
    }

    private void validateModelPrediction(ModelPredictionResponse modelPrediction) {
        if(modelPrediction ==null || modelPrediction.getData()==null || modelPrediction.getData().getPredictionIndex() == null ){
            throw new RuntimeException("Model prediction failed: Model response is null or missing data.");
        }
    }

    private ModelPredictionPrimaryDTO generateResponse(Submission persistedEntity, ModelPredictionResponse modelPrediction,MultipartFile originalInputImage) {
        PredictionDetailResponse modelPredictionResponse =modelPrediction.getData();
        String EncodedOriginalInputImage = ImageUtils.prepareImageForJson(originalInputImage);
        ModelPredictionDetailsResponseDTO modelPredictionDetailsResponseDTO =
                new ModelPredictionDetailsResponseDTO.Builder()
                        .withPredictionIndex(modelPredictionResponse.getPredictionIndex())
                        .withMessage(modelPrediction.getMessage())
                        .withClassProbability(modelPredictionResponse.getClassProbability())
                        .withOverlayImage(modelPredictionResponse.getOverlayImage())
                        .withOriginalInputImage(EncodedOriginalInputImage)
                        .build();

        DiseaseStage diseaseStage =persistedEntity.getDiseaseStage();
        DiseaseStageDetailResponseDTO diseaseStageDetailResponseDTO =
                new DiseaseStageDetailResponseDTO(diseaseStage.getId(), diseaseStage.getName(),diseaseStage.getDiseaseLevel());

        Status status =persistedEntity.getStatus();
        StatusDetailResponseDTO statusDetailResponseDTO =
                new StatusDetailResponseDTO(status.getId(), status.getName());

        UserProfile userProfile = persistedEntity.getUserProfile();
        UserProfileDetailResponseDTO userProfileDetailResponseDTO =
                new UserProfileDetailResponseDTO(userProfile.getId());

        ModelPredictionPrimaryDTO modelPredictionPrimaryDTO =
                new ModelPredictionPrimaryDTO
                        .Builder()
                        .withSubmissionId(persistedEntity.getSubmissionId())
                        .withCreatedDateTime(persistedEntity.getCreatedDateTime())
                        .withIsActive(persistedEntity.getIsActive())
                        .withPatientReferenceId(persistedEntity.getPatientReferenceId())
                        .withStatus(statusDetailResponseDTO)
                        .withUserProfile(userProfileDetailResponseDTO)
                        .withDiseaseStage(diseaseStageDetailResponseDTO)
                        .withHasDisease(persistedEntity.getHasDisease())
                        .withModelPredictionDetails(modelPredictionDetailsResponseDTO)
                        .build();

        return modelPredictionPrimaryDTO;
    }

    private Status getStatusInformationById(long statusId) {
        return this.statusInformation.getStatusById(statusId);
    }
    private DiseaseStage getDiseaseStageInformation(long id){
        return this.diseaseStageInformation.getDiseaseStageById(id);
    }
    private DiseaseStage getDiseaseStageInformationByDiseaseLevel(int diseaseLevel){
        return this.diseaseStageInformation.getDiseaseStageByDiseaseLevel(diseaseLevel);
    }

    private DiseaseStage getRandomDisease(){
      int value=  (int)(Math.random() * ((6 - 1) + 1)) + 1;
      return getDiseaseStageInformation(value);
    }

    private Submission persistEntity(Submission submission) {
       return this.submissionRepository.save(submission);
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
    public  String prepareImageForJson(MultipartFile file) {
        try {
            // 1. Convert MultipartFile → BufferedImage
            BufferedImage image = ImageIO.read(file.getInputStream());

            if (image == null) {
                throw new RuntimeException("Invalid image file");
            }

            // 2. Convert to RGB (ensures 3 channels like your Python code)
            BufferedImage rgbImage = new BufferedImage(
                    image.getWidth(),
                    image.getHeight(),
                    BufferedImage.TYPE_3BYTE_BGR
            );

            rgbImage.getGraphics().drawImage(image, 0, 0, null);

            // 3. Write image to byte array (JPEG)
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(rgbImage, "jpg", baos);

            byte[] imageBytes = baos.toByteArray();

            // 4. Encode to Base64
            return Base64.getEncoder().encodeToString(imageBytes);

        } catch (Exception e) {
            throw new RuntimeException("Error processing image", e);
        }
    }
}
