package com.example;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.metrics.Metric;
import org.springframework.boot.actuate.metrics.repository.MetricRepository;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.stream.module.trigger.TriggerConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.integration.support.MessageBuilder;

@Configuration
@EnableBinding(Source.class)
@Import(TriggerConfiguration.class)
public class MetricSource {

	@Bean
	public IntegrationFlow metricPollingFlow(
			@Qualifier("defaultPoller") PollerMetadata poller,
			Source source,
			MetricRepository repository,
			MetricSourceProperties properties) {

		MessageSource<Number> messageSource =  () -> {
			Metric<?> metric = repository.findOne(properties.getMetricName());
			Number payload = Number.class.cast(metric.getValue());
			return MessageBuilder.withPayload(payload).build();
		};
		return IntegrationFlows
				.from(messageSource, spec -> spec.poller(poller))
				.channel(source.output())
				.get();
	}
}
