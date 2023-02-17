package com.lab.rabbitmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@Slf4j
public class MessageDlqListener {
	
	@RabbitListener(queues = TopicNames.QUEUE_NAME_DLQ)
	public void listener(Mensagem mensagem) throws Exception {
		LocalDateTime lt = LocalDateTime.now();
		log.info(lt.toString());
		log.info("DLQ Reprocessando mensagem: [{}]", mensagem);
		if (mensagem.getStatus() > 2) {
			throw new Exception("Sou a DLQ e não consumo essa mensagem!");
		}
	}

}
