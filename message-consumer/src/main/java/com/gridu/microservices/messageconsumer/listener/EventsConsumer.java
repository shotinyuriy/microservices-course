package com.gridu.microservices.messageconsumer.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class EventsConsumer {

	private Logger LOGGER = LoggerFactory.getLogger(MessageConsumer.class);

	@JmsListener(destination = "events.queue")
	public void consumeFromQueue(String event) {
		LOGGER.info("Event  Received from the QUEUE: "+event);
	}
}
