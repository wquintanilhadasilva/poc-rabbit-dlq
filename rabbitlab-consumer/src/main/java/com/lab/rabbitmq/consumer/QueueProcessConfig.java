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
		args.put("x-dead-letter-exchange", TopicNames.EXCHANGE_MSG_DLQ);
		args.put("x-dead-letter-routing-key", TopicNames.MESSAGE_KEY_ORDERS_DLQ);
			return  new Queue(TopicNames.QUEUE_NAME_PROCESS, true, false, true, args);
	}
	
	@Bean
	public Binding bindingProcess() {
		return BindingBuilder
				.bind(queueProcess())
				.to(exchangeProcess())
				.with(TopicNames.MESSAGE_KEY_ORDERS);
	}
	
//	@Bean
//	public SimpleMessageListenerContainer processContainer(ConnectionFactory connectionFactory,
//														   MessageListenerAdapter processListenerAdapter) {
//		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//		container.setConnectionFactory(connectionFactory);
//		container.setQueues(queueProcess());
//		container.setMessageListener(processListenerAdapter);
//		container.setConcurrentConsumers(5); // define a quantidade de threads que vão processar as mensagens
//		return container;
//	}
	

	
//	@Bean
//	public MessageListenerAdapter processListenerAdapter(MessageListener myService) {
//		return new MessageListenerAdapter(myService, "listener2");
//	}

}
