package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties
public class MetricSourceApplication {

	@Bean
	public MetricSourceProperties metricSourceProperties() {
		return new MetricSourceProperties();
	}

	public static void main(String[] args) {
		SpringApplication.run(MetricSourceApplication.class, args);
	}
}
