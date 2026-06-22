package com.doctor.dr.externalapiservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ModelClientService {
    <T> T performModelPrediction(MultipartFile multipartFile,Class<T> responseType) throws IOException;
}
