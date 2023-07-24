package com.dcf.IdGeneratorSpring.city;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
@Query("SELECT c FROM City c WHERE c.id = ?1")
    Optional<City> findCityById(Long id);
@Query("SELECT c FROM City c WHERE c.name = ?1")
    Optional<City> findCityByName(String name);

}

