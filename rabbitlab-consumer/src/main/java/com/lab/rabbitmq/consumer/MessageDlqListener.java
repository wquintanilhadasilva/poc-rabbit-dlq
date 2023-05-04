package com.lab.rabbitmq.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MessageDlqListener {
	
	
	private final MessageService messageService;
	
	@RabbitListener(queues = TopicNames.QUEUE_NAME_DLQ)
	public void listener(Mensagem mensagem) throws Exception {
		messageService.listenerDlq(mensagem);
	}

}
