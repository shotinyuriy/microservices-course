package com.gridu.microservices.messageproducer.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Queue;
import javax.jms.Topic;

@Configuration
public class JmsConfig {

	@Value("${activemq.broker-url}")
	private String brokerURL;

	@Bean
	public Queue messagesQueue() {
		return new ActiveMQQueue("messages.queue");
	}

	@Bean
	public Topic messagesTopic() {
		return new ActiveMQTopic("messages.topic");
	}

	@Bean
	public Queue eventsQueue() {
		return new ActiveMQQueue("events.queue");
	}

	@Bean
	public ActiveMQConnectionFactory activeMQConnectionFactory() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL(brokerURL);
		return connectionFactory;
	}

	@Bean
	public JmsTemplate queueJmsTemplate() {
		return new JmsTemplate(activeMQConnectionFactory());
	}

	@Bean
	public JmsTemplate topicJmsTemplate() {
		JmsTemplate jmsTemplate = new JmsTemplate(activeMQConnectionFactory());
		jmsTemplate.setPubSubDomain(true);
		return jmsTemplate;
	}
}
