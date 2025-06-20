package io.github.stawkey.roomreservation;

import com.fasterxml.jackson.databind.Module;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FullyQualifiedAnnotationBeanNameGenerator;

@SpringBootApplication(
    nameGenerator = FullyQualifiedAnnotationBeanNameGenerator.class
)
@ComponentScan(
    basePackages = {
            "org.openapitools",
            "io.github.stawkey.roomreservation.api",
            "io.github.stawkey.roomreservation.controller",
            "io.github.stawkey.roomreservation.config",
            "io.github.stawkey.roomreservation.entity",
            "io.github.stawkey.roomreservation.repository",
            "io.github.stawkey.roomreservation.mapper",
            "io.github.stawkey.roomreservation.service"
    },
    nameGenerator = FullyQualifiedAnnotationBeanNameGenerator.class
)
public class OpenApiGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenApiGeneratorApplication.class, args);
    }

    @Bean(name = "io.github.stawkey.roomreservation.OpenApiGeneratorApplication.jsonNullableModule")
    public Module jsonNullableModule() {
        return new JsonNullableModule();
    }

}