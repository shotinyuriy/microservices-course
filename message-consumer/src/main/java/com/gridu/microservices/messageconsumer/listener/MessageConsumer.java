package com.gridu.microservices.messageconsumer.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

	private Logger LOGGER = LoggerFactory.getLogger(MessageConsumer.class);

	@JmsListener(destination = "messages.queue")
	public void consumeFromQueue(String message) {
		LOGGER.info("Message Received from the QUEUE: "+message);
	}

	@JmsListener(destination = "messages.topic", containerFactory = "topicListenerContainerFactory")
	public void consumeFromTopic1(String message) {
		LOGGER.info("1: Message Received from the TOPIC: "+message);
	}

	@JmsListener(destination = "messages.topic", containerFactory = "topicListenerContainerFactory")
	public void consumeFromTopic2(String message) {
		LOGGER.info("2: Message Received from the TOPIC: "+message);
	}
}
