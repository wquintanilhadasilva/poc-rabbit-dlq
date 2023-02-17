package com.lab.rabbitmq.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {
	
	private final AmqpTemplate rabbitTemplate;
	
	public void sendMessage(Mensagem mensagem) {
		mensagem.setTime(LocalDateTime.now());
		log.info("Enviando a mensagem [{}]", mensagem);
		rabbitTemplate.convertAndSend(TopicNames.EXCHANGE_MSG, TopicNames.MESSAGE_KEY, mensagem);
	}
	
}
