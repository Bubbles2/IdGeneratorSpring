package com.dcf.IdGeneratorSpring.city;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CityConfig {
    @Bean
    CommandLineRunner commandLineRunner(CityRepository cityRepository) {
        return args -> {
            City paris = new City("Paris", "01", 0);
            City lyon = new City("Lyon", "69", 0);
            City marseille = new City( "Marseille","12", 0);
            cityRepository.save(paris);
            cityRepository.save(lyon);
            cityRepository.save(marseille);
        };
    }
}
