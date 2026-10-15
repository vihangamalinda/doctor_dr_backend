package com.doctor.dr.submission.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class PredictionDetailResponse {
    @JsonProperty("class_probability")
    private Map<String, Double> classProbability;

    @JsonProperty("overlay_image")
    String overlayImage;

    @JsonProperty("prediction_index")
    Integer predictionIndex;

    public Map<String, Double> getClassProbability() {
        return classProbability;
    }

    public void setClassProbability(Map<String, Double> classProbability) {
        this.classProbability = classProbability;
    }

    public String getOverlayImage() {
        return overlayImage;
    }

    public void setOverlayImage(String overlayImage) {
        this.overlayImage = overlayImage;
    }

    public Integer getPredictionIndex() {
        return predictionIndex;
    }

    public void setPredictionIndex(Integer predictionIndex) {
        this.predictionIndex = predictionIndex;
    }
}
