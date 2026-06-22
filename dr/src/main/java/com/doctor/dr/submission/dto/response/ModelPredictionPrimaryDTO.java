package com.doctor.dr.submission.dto.response;

import java.time.LocalDateTime;

public class ModelPredictionPrimaryDTO {
    private final Long submissionId;

    private final String patientReferenceId;
    private final LocalDateTime createdDateTime;
    private final boolean isActive;
    private final boolean hasDisease;
    private final DiseaseStageDetailResponseDTO diseaseStage;
    private final StatusDetailResponseDTO status;
    private final UserProfileDetailResponseDTO userProfile;

    private final ModelPredictionDetailsResponseDTO modelPredictionDetails;

    private ModelPredictionPrimaryDTO(Builder builder){
        this.submissionId = builder.submissionId;
        this.createdDateTime = builder.createdDateTime;
        this.diseaseStage=builder.diseaseStage;
        this.patientReferenceId = builder.patientReferenceId;
        this.hasDisease = builder.hasDisease;
        this.isActive = builder.isActive;
        this.status = builder.status;
        this.userProfile = builder.userProfile;
        this.modelPredictionDetails = builder.modelPredictionDetails;
    }

    public Long getSubmissionId() {
        return submissionId;
    }

    public String getPatientReferenceId() {
        return patientReferenceId;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isHasDisease() {
        return hasDisease;
    }

    public DiseaseStageDetailResponseDTO getDiseaseStage() {
        return diseaseStage;
    }

    public StatusDetailResponseDTO getStatus() {
        return status;
    }

    public UserProfileDetailResponseDTO getUserProfile() {
        return userProfile;
    }

    public ModelPredictionDetailsResponseDTO getModelPredictionDetails() {
        return modelPredictionDetails;
    }

    public static class Builder {
        private  Long submissionId;
        private  String patientReferenceId;
        private  LocalDateTime createdDateTime;
        private  boolean isActive;
        private  boolean hasDisease;
        private  DiseaseStageDetailResponseDTO diseaseStage;
        private  StatusDetailResponseDTO status;
        private  UserProfileDetailResponseDTO userProfile;
        private ModelPredictionDetailsResponseDTO modelPredictionDetails;

        public Builder(){

        }

        public Builder withSubmissionId(Long submissionId){
            this.submissionId = submissionId;
            return this;
        }
        public Builder withPatientReferenceId(String patientReferenceId){
            this.patientReferenceId = patientReferenceId;
            return this;
        }
        public Builder withIsActive(boolean isActive){
            this.isActive = isActive;
            return this;
        }
        public Builder withHasDisease(boolean hasDisease){
            this.hasDisease = hasDisease;
            return this;
        }
        public Builder withDiseaseStage(DiseaseStageDetailResponseDTO diseaseStage){
            this.diseaseStage = diseaseStage;
            return this;
        }
        public Builder withStatus(StatusDetailResponseDTO status){
            this.status = status;
            return this;
        }
        public Builder withCreatedDateTime(LocalDateTime createdDateTime){
            this.createdDateTime = createdDateTime;
            return this;
        }
        public Builder withUserProfile(UserProfileDetailResponseDTO userProfile){
            this.userProfile = userProfile;
            return this;
        }
        public Builder withModelPredictionDetails(ModelPredictionDetailsResponseDTO modelPredictionDetails){
            this.modelPredictionDetails = modelPredictionDetails;
            return this;
        }

        public ModelPredictionPrimaryDTO build(){
            return new ModelPredictionPrimaryDTO(this);
        }
    }


}
