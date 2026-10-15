package com.doctor.dr;

import com.doctor.dr.internalsystemconfiguration.InternalSystemConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(InternalSystemConfiguration.class)
public class DrApplication {

	public static void main(String[] args) {
		SpringApplication.run(DrApplication.class, args);
	}

}
