package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.Metric;
import org.springframework.boot.actuate.metrics.repository.MetricRepository;
import org.springframework.boot.actuate.metrics.writer.Delta;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.example.MetricSourceApplicationTests.KEY;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {MetricSourceApplication.class, RedisAutoConfiguration.class})
@DirtiesContext
@IntegrationTest("metricName=" + KEY)
public class MetricSourceApplicationTests {

	static final String KEY = "counter.message.true";
	static final long VALUE = 10L;

	@Configuration
	public static class TestConfig {

		@Bean
		MetricRepository metricRepository() {
			return new MetricRepository() {
				@Override
				public Metric<?> findOne(String metricName) {
					if (metricName.equals(KEY))
						return new Metric<Number>(KEY, VALUE);
					return null;
				}

				@Override
				public Iterable<Metric<?>> findAll() {
					return null;
				}

				@Override
				public long count() {
					return 0;
				}

				@Override
				public void increment(Delta<?> delta) {
				}

				@Override
				public void reset(String metricName) {
				}

				@Override
				public void set(Metric<?> value) {
				}
			};
		}
	}


	@Autowired
	private MessageCollector messageCollector;

	@Autowired
	private Source source;

	@Test
	public void testExtraction() throws InterruptedException {
		Message<?> received = messageCollector.forChannel(source.output()).poll(
				5, SECONDS);
		assertNotNull(received);
		assertNotNull(received.getPayload());
		assertTrue(Number.class.isAssignableFrom(received.getPayload().getClass()));
		assertTrue(received.getPayload().equals(VALUE));
		Number number = Number.class.cast(received.getPayload());
		assertTrue(number.intValue() > 0);
	}
}
