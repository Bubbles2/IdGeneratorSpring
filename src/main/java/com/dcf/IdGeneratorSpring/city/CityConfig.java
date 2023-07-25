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
//    @Autowired
//    @PersistenceContext
//    EntityManager entityManager;
@Autowired
private JdbcTemplate jdbcTemplate;
    @Bean
    CommandLineRunner commandLineRunner(CityRepository cityRepository) {
        return args -> {
// Save wont work because resets default value to null
 //           jdbcTemplate.update("INSERT INTO City(name, cityCode) VALUES (?, ?)", "Paris", "99");
            City paris = new City("Paris", "01");
            City lyon = new City("Lyon", "69");
            City marseille = new City( "Marseille","12");



//                entityManager.createNativeQuery("INSERT INTO City(name, cityCode) VALUES (?, ?)")
//                        .setParameter(1, "Paris")
//                        .setParameter(2, "01")
//                        .executeUpdate();

            cityRepository.save(paris);
            cityRepository.save(lyon);
            cityRepository.save(marseille);
        };
    }
}
