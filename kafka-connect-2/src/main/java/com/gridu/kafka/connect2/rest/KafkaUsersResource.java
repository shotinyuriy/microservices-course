package com.gridu.kafka.connect2.rest;

import com.gridu.kafka.connect2.service.KafkaUserProducer;
import com.gridu.kafka.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/kafka")
public class KafkaUsersResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaUsersResource.class);
	private final KafkaUserProducer producer;

	private static final AtomicLong USER_ID = new AtomicLong(0L);

	@Autowired
	public KafkaUsersResource(KafkaUserProducer producer) {
		this.producer = producer;
	}

	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@RequestBody User newUser) {
		newUser.setId(USER_ID.incrementAndGet());
		LOGGER.info("Sending new user "+newUser);
		producer.sendMessage(newUser);
		return ResponseEntity.ok("User created with id: "+newUser.getId());
	}
}
