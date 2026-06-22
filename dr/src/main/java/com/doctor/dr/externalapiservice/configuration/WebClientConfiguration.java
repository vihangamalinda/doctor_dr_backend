package com.doctor.dr.externalapiservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

    public static final String MODEL_SERVICE_BASE_URL = "http://127.0.0.1:5000";

    @Bean
    public WebClient webClient(){
        return WebClient.builder()
                .baseUrl(MODEL_SERVICE_BASE_URL) // Set the base URL for your API
                .build();
    }
}
