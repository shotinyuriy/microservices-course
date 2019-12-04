package com.gridu.microservices.kafkaconnect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class GriduKafkaListener {

	private static Logger LOG = LoggerFactory.getLogger(GriduKafkaListener.class);

	@KafkaListener(topics="gridu-topic")
	public void listenWithHeaders(
		@Payload String message,
	    @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition
	) {
		LOG.info("Partition: "+partition+"; Received message "+message);
	}
}
