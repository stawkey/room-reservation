package io.github.stawkey.roomreservation.config;

import io.github.stawkey.roomreservation.dto.ReservationStatus;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

@Configuration
public class EnumConverterConfiguration {

    @Bean(name = "org.openapitools.configuration.EnumConverterConfiguration.reservationStatusConverter")
    Converter<String, ReservationStatus> reservationStatusConverter() {
        return new Converter<String, ReservationStatus>() {
            @Override
            public ReservationStatus convert(String source) {
                return ReservationStatus.fromValue(source);
            }
        };
    }

}
