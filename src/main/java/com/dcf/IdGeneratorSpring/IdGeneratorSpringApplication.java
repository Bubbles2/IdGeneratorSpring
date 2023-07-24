package com.dcf.IdGeneratorSpring;

import com.dcf.IdGeneratorSpring.city.City;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication

public class IdGeneratorSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdGeneratorSpringApplication.class, args);
	}

}
