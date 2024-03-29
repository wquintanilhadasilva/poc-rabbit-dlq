package com.lab.rabbitmq.consumer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RabbitConfig {
	
	@Value("${spring.rabbitmq.listener.simple.consumers:1}")
	private int consumers;
	
	@Value("${spring.rabbitmq.listener.simple.dlq-consumers:1}")
	private int dlqConsumers;
	
	@Value("${spring.rabbitmq.listener.simple.prefetch:5}")
	private int prefetchCount;

	@Bean
	public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
		return new RabbitAdmin(connectionFactory);
	}
	
	@Bean
	public ApplicationListener<ApplicationReadyEvent> applicationReadyEventApplicationListener(RabbitAdmin rabbitAdmin) {
		return event -> rabbitAdmin.initialize();
	}

	@Bean
	public Jackson2JsonMessageConverter messageConverter() {
		final ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.registerModule(new JavaTimeModule());
		return new Jackson2JsonMessageConverter(mapper);
	}
	
	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter messageConverter) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(messageConverter);
		return rabbitTemplate;
	}
	
	@Bean
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory,
																			   Jackson2JsonMessageConverter messageConverter) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setMessageConverter(messageConverter);
		log.info("Número de consumidores [{}]", consumers);
		factory.setConcurrentConsumers(consumers);
		log.info("Máximo de confirmação de leitura por vez [{}]", prefetchCount);
		factory.setPrefetchCount(prefetchCount);
		factory.setErrorHandler(new ConditionalRejectingErrorHandler(new ConditionalRejectingErrorHandler.DefaultExceptionStrategy()));
		
		// configura o retry utilizando o RetryInterceptorBuilder
		RetryInterceptorBuilder retryInterceptorBuilder = RetryInterceptorBuilder.stateless()
				.maxAttempts(3)
				.backOffOptions(20000, 2.0, 60000) // intervalo inicial de 20 segundos, fator multiplicador de 2.0, tempo máximo de espera de 60 segundos
				.recoverer(new RejectAndDontRequeueRecoverer()); // rejeita as mensagens e não as reenfileira
		
		factory.setAdviceChain(retryInterceptorBuilder.build()); // aplica o interceptor de retry no listener
		
		return factory;
	}
	
	@Bean
	public SimpleRabbitListenerContainerFactory dlqRabbitListenerContainerFactory(ConnectionFactory connectionFactory,
																			   Jackson2JsonMessageConverter messageConverter) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setMessageConverter(messageConverter);
		log.info("DLQ -> Número de consumidores [{}]", dlqConsumers);
		factory.setConcurrentConsumers(dlqConsumers);
		log.info("DLQ -> Máximo de confirmação de leitura por vez [{}]", prefetchCount);
		factory.setPrefetchCount(prefetchCount);
		factory.setErrorHandler(new ConditionalRejectingErrorHandler(new ConditionalRejectingErrorHandler.DefaultExceptionStrategy()));

		// configura o retry utilizando o RetryInterceptorBuilder
		RetryInterceptorBuilder retryInterceptorBuilder = RetryInterceptorBuilder.stateless()
				.maxAttempts(4)
				.backOffOptions(10000, 1.5, 60000) // intervalo inicial de 10 segundos, fator multiplicador de 1.5, tempo máximo de espera de 40 segundos
				.recoverer(new RejectAndDontRequeueRecoverer()); // rejeita as mensagens e não as reenfileira

		factory.setAdviceChain(retryInterceptorBuilder.build()); // aplica o interceptor de retry no listener

		return factory;
	}
	
}
