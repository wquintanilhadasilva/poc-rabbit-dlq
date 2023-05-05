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
		Map<String, Object> args = new HashMap<String, Object>(){{
			put(X_RETRY_HEADER, 3);
			put(X_MESSAGE_TTL, 30000); //30s
			put("x-max-retries", 3); // número máximo de tentativas de entrega
			put("x-initial-interval", 10000); // intervalo inicial entre tentativas em milissegundos
			put(TopicNames.QUEUE_ATTRIBUTE_DLQ_EXCHANGE, TopicNames.EXCHANGE_MSG_MANUAL);
			put(TopicNames.QUEUE_ATTRIBUTE_DLQ_ROUTING_KEY, TopicNames.MESSAGE_KEY_ORDERS_MANUAL);
			put("x-max-interval-multiplier", 3); // fator multiplicador para calcular o intervalo de tentativas subsequentes
		}};
//		args.put(X_RETRY_HEADER, 3);
//		args.put(X_MESSAGE_TTL, 30000); //30s
//		args.put("x-max-retries", 3); // número máximo de tentativas de entrega
//		args.put("x-initial-interval", 10000); // intervalo inicial entre tentativas em milissegundos
//		args.put(TopicNames.QUEUE_ATTRIBUTE_DLQ_EXCHANGE, TopicNames.EXCHANGE_MSG_MANUAL);
//		args.put(TopicNames.QUEUE_ATTRIBUTE_DLQ_ROUTING_KEY, TopicNames.MESSAGE_KEY_ORDERS_MANUAL);
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
