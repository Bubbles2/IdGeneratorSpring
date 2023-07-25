package com.dcf.IdGeneratorSpring.city;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class CityController {
    private CityService cityService;
@Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/cities")
    public List<City> getCities() {
        return cityService.getCities();
    }
    @GetMapping("/cityId")
    public ResponseEntity generateId(@RequestParam String city1, @RequestParam String city2) {
        String id = cityService.generateId(city1, city2);
        System.out.println("city1: " + city1);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

@PostMapping
    public void addCity(@RequestBody City city) {
        cityService.addCity(city);
    }
}

