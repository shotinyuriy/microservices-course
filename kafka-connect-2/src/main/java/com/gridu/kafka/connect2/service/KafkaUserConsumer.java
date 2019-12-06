package com.gridu.kafka.connect2.service;

import com.gridu.kafka.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaUserConsumer {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaUserConsumer.class);

	@KafkaListener(topics = "user-message", groupId = "${spring.kafka.consumer.group_id}")
	public void consumeUser(User userMessage) {
		LOGGER.info("Consumer user message: "+userMessage);
	}

}
