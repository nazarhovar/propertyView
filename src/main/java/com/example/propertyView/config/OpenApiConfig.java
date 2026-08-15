package com.example.propertyView.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI hotelApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Property View API")
                        .version("1.0")
                        .description(
                                "REST API for managing hotels"
                        ));
    }
}