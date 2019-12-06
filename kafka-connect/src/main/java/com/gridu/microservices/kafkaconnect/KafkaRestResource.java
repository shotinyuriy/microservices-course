package com.gridu.microservices.kafkaconnect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/kafka")
public class KafkaRestResource {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Value(value="${kafka.topics.gridu.name}")
	private String griduTopicName;

	@PostMapping("/gridu/messages")
	public String postGriduMessages(@RequestBody String message) {
		ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(griduTopicName, message);
		try {
			SendResult<String, String> sendResult = future.get(5000, TimeUnit.MILLISECONDS);
			return "Message Posted Successfully. " + sendResult.toString();
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			return "Message Send Failed. "+e.toString();
		}
	}
}