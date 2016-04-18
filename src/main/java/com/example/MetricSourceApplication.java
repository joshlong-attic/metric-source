package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.ExportMetricReader;
import org.springframework.boot.actuate.autoconfigure.ExportMetricWriter;
import org.springframework.boot.actuate.metrics.repository.MetricRepository;
import org.springframework.boot.actuate.metrics.repository.redis.RedisMetricRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;

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
