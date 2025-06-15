package io.github.stawkey.roomreservation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SpringDocConfiguration {

    @Bean(name = "org.openapitools.configuration.SpringDocConfiguration.apiInfo")
    OpenAPI apiInfo() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("RoomReservation")
                                .description("A universal room reservation system designed for use in various institutions and organizations.  Possible applications: - universities, schools – lecture halls, laboratories - companies, hotels – conference rooms - libraries, cultural centers – workshop rooms, event spaces - gyms and fitness clubs – exercise rooms ")
                                .version("0.0.1")
                )
        ;
    }
}