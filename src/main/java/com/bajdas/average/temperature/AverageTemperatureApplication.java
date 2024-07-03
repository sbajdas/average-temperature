package com.bajdas.average.temperature;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AverageTemperatureApplication {

	public static void main(String[] args) {
		SpringApplication.run(AverageTemperatureApplication.class, args);
	}

}
