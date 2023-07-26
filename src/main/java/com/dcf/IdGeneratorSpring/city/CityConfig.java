package com.dcf.IdGeneratorSpring.city;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class CityConfig {

@Autowired
private JdbcTemplate jdbcTemplate;
    @Bean
    CommandLineRunner commandLineRunner(CityRepository cityRepository, CityService cityService) {
        return args -> {
            City paris = new City("Paris", "01");
            City marseille = new City( "Marseille","12");
            cityService.saveCity(paris, "paris_sequence");
             cityService.saveCity(marseille, "marseille_sequence");
        };
    }
}
