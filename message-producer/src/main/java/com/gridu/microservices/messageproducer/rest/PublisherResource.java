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
	private Queue queue;

	@Autowired
	private Topic topic;

	@PostMapping("/messages/queue")
	public String postMessage(@RequestBody String message) {
		getQueueJmsTemplate().convertAndSend(getQueue(), message);
		String queueName = null;
		try {
			queueName = getQueue().getQueueName();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return "Message Published Successfully to a QUEUE: "+queueName;
	}

	@PostMapping("/messages/topic")
	public String postMessageToTopic(@RequestBody String message) {
		getTopicJmsTemplate().convertAndSend(getTopic(), message);
		String topicName = null;
		try {
			topicName = getTopic().getTopicName();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return "Message Published Successfully to a TOPIC: "+topicName;
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

	public Queue getQueue() {
		return queue;
	}

	public void setQueue(Queue queue) {
		this.queue = queue;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}
}
