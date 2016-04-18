package com.example;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class MetricSourceProperties {
	private String metricName ;

	public String getMetricName() {
		return metricName;
	}

	public void setMetricName(String metricName) {
		this.metricName = metricName;
	}
}
