package com.lab.rabbitmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@Slf4j
public class MessageService {

//	@Async // Não pode ser assíncrono senão vai commitar a transação no rabbit como se tivesse concluído com sucesso e não vai processar as dlqs
	public void listener(Mensagem mensagem) throws Exception {
		LocalDateTime lt = LocalDateTime.now();
		log.info(lt.toString());
		log.info("Processando mensagem: [{}]", mensagem);
		if (mensagem.getStatus() != 1) {
			throw new AmqpRejectAndDontRequeueException("Sou o processador e NÃO consumo essa mensagem: [" + mensagem + "]");
		}
		log.info("Sou o processador e CONSUMO essa mensagem: [{}]", mensagem);
	}

//	@Async
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
