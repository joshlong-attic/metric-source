package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {MetricSourceApplication.class, RedisAutoConfiguration.class})
@DirtiesContext
@IntegrationTest("metricName=counter.message.true")
public class MetricSourceApplicationTests {

	@Autowired
	private MessageCollector messageCollector;

	@Autowired
	private Source source;

	@Test
	public void testExtraction() throws InterruptedException {
		Message<?> received = messageCollector.forChannel(source.output()).poll(10, TimeUnit.SECONDS);
		assertNotNull(received);
		assertNotNull(received.getPayload());
		assertTrue(Number.class.isAssignableFrom(received.getPayload().getClass()));
		Number number = Number.class.cast(received.getPayload());
		assertTrue(number.intValue() > 0);
	}
}
