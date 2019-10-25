package com.gridu.microservices.messageconsumer.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Queue;

@Configuration
public class JmsConfig {

	@Value("${activemq.broker-url}")
	private String brokerURL;

	@Bean
	public Queue activeMQQueue() {
		return new ActiveMQQueue("messages.queue");
	}

	@Bean
	public ActiveMQConnectionFactory activeMQConnectionFactory() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL(brokerURL);
		return connectionFactory;
	}

	@Bean
	public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
		DefaultJmsListenerContainerFactory factory =
			new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(activeMQConnectionFactory());
		return factory;
	}

	@Bean
	public DefaultJmsListenerContainerFactory topicListenerContainerFactory() {
		DefaultJmsListenerContainerFactory factory =
			new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(activeMQConnectionFactory());
		factory.setPubSubDomain(true);
		return factory;
	}
}
