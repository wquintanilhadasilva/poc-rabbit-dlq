package com.lab.rabbitmq.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MessageListener {
	
	private final MessageService messageService;
	
	@RabbitListener(queues = TopicNames.QUEUE_NAME_PROCESS, containerFactory = "rabbitListenerContainerFactory")
	public void listener(Mensagem mensagem) throws Exception {
		messageService.listener(mensagem);
	}

}
