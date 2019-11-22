package com.gridu.microservices.messageproducer.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Topic;

@RestController
@RequestMapping("/publisher")
public class PublisherResource {

	@Autowired
	private JmsTemplate queueJmsTemplate;

	@Autowired
	private JmsTemplate topicJmsTemplate;

	@Autowired
	private Queue messagesQueue;

	@Autowired
	private Topic messagesTopic;

	@Autowired
	private Queue eventsQueue;

	@PostMapping("/messages/queue")
	public String postMessage(@RequestBody String message) {
		getQueueJmsTemplate().convertAndSend(getMessagesQueue(), message);
		String queueName = null;
		try {
			queueName = getMessagesQueue().getQueueName();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return "Message Published Successfully to a QUEUE: "+queueName;
	}

	@PostMapping("/messages/topic")
	public String postMessageToTopic(@RequestBody String message) {
		getTopicJmsTemplate().convertAndSend(getMessagesTopic(), message);
		String topicName = null;
		try {
			topicName = getMessagesTopic().getTopicName();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return "Message Published Successfully to a TOPIC: "+topicName;
	}

	@PostMapping("/events/queue")
	public String postEventQueue(@RequestBody String event) {
		getQueueJmsTemplate().convertAndSend(getEventsQueue(), event);
		String queueName = null;
		try {
			queueName = getEventsQueue().getQueueName();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return "Event Published Successfully to a QUEUE: "+queueName;
	}

	public JmsTemplate getQueueJmsTemplate() {
		return queueJmsTemplate;
	}

	public void setQueueJmsTemplate(JmsTemplate queueJmsTemplate) {
		this.queueJmsTemplate = queueJmsTemplate;
	}

	public JmsTemplate getTopicJmsTemplate() {
		return topicJmsTemplate;
	}

	public void setTopicJmsTemplate(JmsTemplate topicJmsTemplate) {
		this.topicJmsTemplate = topicJmsTemplate;
	}

	public Queue getMessagesQueue() {
		return messagesQueue;
	}

	public void setMessagesQueue(Queue queue) {
		this.messagesQueue = queue;
	}

	public Topic getMessagesTopic() {
		return messagesTopic;
	}

	public void setMessagesTopic(Topic topic) {
		this.messagesTopic = topic;
	}

	public Queue getEventsQueue() {
		return eventsQueue;
	}

	public void setEventsQueue(Queue eventsQueue) {
		this.eventsQueue = eventsQueue;
	}
}
