package com.cg.healthcare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class HealthCareMasterApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthCareMasterApplication.class, args);
	}

}
