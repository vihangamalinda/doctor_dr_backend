package com.doctor.dr.submission.dto.external;

public class ModelPredictionResponse {
    private PredictionDetailResponse data;

    private String message;

    public PredictionDetailResponse getData() {
        return data;
    }

    public void setData(PredictionDetailResponse data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
