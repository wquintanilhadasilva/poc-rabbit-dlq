package com.lab.rabbitmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@Slf4j
public class MessageService {
	
	public void listener(Mensagem mensagem) throws Exception {
		LocalDateTime lt = LocalDateTime.now();
		log.info(lt.toString());
		log.info("Processando mensagem: [{}]", mensagem);
		if (mensagem.getStatus() != 1) {
			throw new AmqpRejectAndDontRequeueException("Sou o processador e NÃO consumo essa mensagem: [" + mensagem + "]");
		}
		log.info("Sou o processador e CONSUMO essa mensagem: [{}]", mensagem);
	}
	
	public void listenerDlq(Mensagem mensagem) throws Exception {
		LocalDateTime lt = LocalDateTime.now();
		log.info(lt.toString());
		log.info("DLQ Reprocessando mensagem: [{}]", mensagem);
		if (mensagem.getStatus() > 2) {
			throw new AmqpRejectAndDontRequeueException("Sou a DLQ e NÃO consumo essa mensagem: [" + mensagem + "]");
		}
		log.info("Sou o DLQ e CONSUMO essa mensagem: [{}]", mensagem);
	}
	
}
