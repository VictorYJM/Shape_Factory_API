package com.example.SF;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.SF.Repository")
@EntityScan(basePackages = "com.example.SF.Model")
public class SfApplication {
	public static void main(String[] args) {
		SpringApplication.run(SfApplication.class, args);
	}
}
