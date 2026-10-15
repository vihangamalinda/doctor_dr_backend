package com.doctor.dr.submission.dto.response;

import java.util.Map;

public class ModelPredictionDetailsResponseDTO {
    private final String message;

    private final Map<String, Double> classProbability;

    private final String overlayImage;

    private final String originalInputImage;

    private final Integer predictionIndex;

    private ModelPredictionDetailsResponseDTO(Builder builder){
        this.message = builder.message;
        this.classProbability = builder.classProbability;
        this.overlayImage = builder.overlayImage;
        this.predictionIndex = builder.predictionIndex;
        this.originalInputImage = builder.originalInputImage;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, Double> getClassProbability() {
        return classProbability;
    }

    public String getOverlayImage() {
        return overlayImage;
    }

    public Integer getPredictionIndex() {
        return predictionIndex;
    }

    public String getOriginalInputImage() {
        return originalInputImage;
    }

    public static class Builder{
        private String message;
        private Map<String, Double> classProbability;
        private String overlayImage;
        private String originalInputImage;
        private Integer predictionIndex;

        public Builder withMessage(String message){
            this.message = message;
            return this;
        }

        public Builder withClassProbability(Map<String, Double> classProbability){
            this.classProbability = classProbability;
            return this;
        }

        public Builder withOverlayImage(String overlayImage){
            this.overlayImage = overlayImage;
            return this;
        }

        public Builder withPredictionIndex(Integer predictionIndex){
            this.predictionIndex = predictionIndex;
            return this;
        }
        public Builder withOriginalInputImage(String originalInputImage){
            this.originalInputImage = originalInputImage;
            return this;
        }

        public ModelPredictionDetailsResponseDTO build(){
            return new ModelPredictionDetailsResponseDTO(this);
        }
    }

}
