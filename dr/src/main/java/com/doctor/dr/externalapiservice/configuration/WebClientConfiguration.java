package com.doctor.dr.externalapiservice.configuration;

import io.netty.channel.ChannelOption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.netty.http.client.HttpClient;
import java.time.Duration;

@Configuration
public class WebClientConfiguration {

//    public static final String MODEL_SERVICE_BASE_URL = "http://127.0.0.1:5000";
    @Value("${model.service.base-url}")
    private String modelServiceBaseUrl;

    @Bean
    public WebClient webClient(){
        HttpClient httpClient = HttpClient.create().option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000).responseTimeout(Duration.ofSeconds(30));

        return WebClient.builder()
                .baseUrl(modelServiceBaseUrl) // Set the base URL for your API
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
