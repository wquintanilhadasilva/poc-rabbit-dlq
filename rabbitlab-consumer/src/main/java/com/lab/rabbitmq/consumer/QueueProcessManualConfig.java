package com.lab.rabbitmq.consumer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueProcessManualConfig {
	
	@Bean
	public TopicExchange exchangeManual() {
		return new TopicExchange(TopicNames.EXCHANGE_MSG_MANUAL);
	}
	
	@Bean
	public Queue queueManual() {
		return  new Queue(TopicNames.QUEUE_NAME_MANUAL, true, false, false);
	}
	
	@Bean
	public Binding bindingManual() {
		return BindingBuilder
				.bind(queueManual())
				.to(exchangeManual())
				.with(TopicNames.MESSAGE_KEY_ORDERS_MANUAL);
	}

}
