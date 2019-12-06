package com.gridu.kafka.connect2.service;

import com.gridu.kafka.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaUserProducer {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaUserProducer.class);
	private static final String TOPIC = "user-message";

	@Autowired
	private KafkaTemplate<String, User> kafkaTemplate;


	public void sendMessage(User userMessage) {
		LOGGER.info("Producing user message, user: "+userMessage);
		kafkaTemplate.send(TOPIC, userMessage);
	}
}