package com.doctor.dr.openapidocumentation;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI defineOpenApi(){
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("Development");

        Contact contact = new Contact();
        contact.setName("Vihanga Malinda");
        contact.setEmail("doctordrapplication@gmail.com");

        Info info = new Info().title("Doctor DR API Documentation")
                .version("1.0.0")
                .description("This is the API documentation for Doctor DR application.")
                .contact(contact);

        return  new OpenAPI().info(info).servers(List.of(server));
    }
}
