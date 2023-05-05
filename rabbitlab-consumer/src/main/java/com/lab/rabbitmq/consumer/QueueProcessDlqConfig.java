package com.lab.rabbitmq.consumer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class QueueProcessDlqConfig {
	
	private static final String X_RETRY_HEADER = "x-dlq-retry";
	private static final String X_MESSAGE_TTL = "x-message-ttl";
	
	@Bean
	public TopicExchange exchangeDlq() {
		return new TopicExchange(TopicNames.EXCHANGE_MSG_DLQ);
	}
	
	@Bean
	public Queue queueDlq() {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put(X_RETRY_HEADER, 3);
		args.put(X_MESSAGE_TTL, 30000); //30s
		args.put(TopicNames.QUEUE_ATTRIBUTE_DLQ_EXCHANGE, TopicNames.EXCHANGE_MSG_MANUAL);
		args.put(TopicNames.QUEUE_ATTRIBUTE_DLQ_ROUTING_KEY, TopicNames.MESSAGE_KEY_ORDERS_MANUAL);
		return  new Queue(TopicNames.QUEUE_NAME_DLQ, true, false, true, args);
	}
	
	@Bean
	public Binding bindingDlq() {
		return BindingBuilder
				.bind(queueDlq())
				.to(exchangeDlq())
				.with(TopicNames.MESSAGE_KEY_ORDERS_DLQ);
	}

}
