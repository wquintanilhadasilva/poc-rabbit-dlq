package com.lab.rabbitmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@Slf4j
public class MessageListener {
	
	@RabbitListener(queues = TopicNames.QUEUE_NAME_PROCESS)
	public void listener(Mensagem mensagem) throws Exception {
		LocalDateTime lt = LocalDateTime.now();
		log.info(lt.toString());
		log.info("Processando mensagem: [{}]", mensagem);
		if (mensagem.getStatus() != 1) {
			throw new Exception("Sou o processador e consumo essa mensagem!");
		}
	}

}
