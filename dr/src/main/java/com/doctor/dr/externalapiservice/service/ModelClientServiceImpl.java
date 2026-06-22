package com.doctor.dr.externalapiservice.service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

@Service
public class ModelClientServiceImpl implements ModelClientService{
    private final WebClient webClient;

    public ModelClientServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    public <T> T performModelPrediction(MultipartFile multipartFile,Class<T> responseType) throws IOException {
        String uri = "/perform-diagnosis";

            ByteArrayResource fileResource  =getByteArrayResource(multipartFile);


        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        bodyBuilder.part("file", fileResource)
                .contentType(MediaType.APPLICATION_OCTET_STREAM);

        return  webClient
                .post()
                .uri(uri)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .bodyValue(bodyBuilder.build())
                .retrieve()
                .bodyToMono(responseType)
                .block();// blocking for simplicity
    }

    private ByteArrayResource getByteArrayResource(MultipartFile multipartFile) throws IOException {
        return new ByteArrayResource(multipartFile.getBytes()) {
            @Override
            public String getFilename() {
                return multipartFile.getOriginalFilename();
            }
        };
    }
}

