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
public class QueueProcessConfig {
	
	@Bean
	public TopicExchange exchangeProcess() {
		return new TopicExchange(TopicNames.EXCHANGE_MSG);
	}
	
	@Bean
	public Queue queueProcess() {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put(TopicNames.QUEUE_ATTRIBUTE_DLQ_EXCHANGE, TopicNames.EXCHANGE_MSG_DLQ);
		args.put(TopicNames.QUEUE_ATTRIBUTE_DLQ_ROUTING_KEY, TopicNames.MESSAGE_KEY_ORDERS_DLQ);
			return  new Queue(TopicNames.QUEUE_NAME_PROCESS, true, false, true, args);
	}
	
	@Bean
	public Binding bindingProcess() {
		return BindingBuilder
				.bind(queueProcess())
				.to(exchangeProcess())
				.with(TopicNames.MESSAGE_KEY_ORDERS);
	}

}
