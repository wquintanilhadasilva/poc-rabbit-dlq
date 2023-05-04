package com.lab.rabbitmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@Slf4j
public class MessageService {
	
	@Async
	public void listener(Mensagem mensagem) throws Exception {
		LocalDateTime lt = LocalDateTime.now();
		log.info(lt.toString());
		log.info("Processando mensagem: [{}]", mensagem);
		if (mensagem.getStatus() != 1) {
			throw new Exception("Sou o processador e consumo essa mensagem!");
		}
	}
	
	@Async
	public void listenerDlq(Mensagem mensagem) throws Exception {
		LocalDateTime lt = LocalDateTime.now();
		log.info(lt.toString());
		log.info("DLQ Reprocessando mensagem: [{}]", mensagem);
		if (mensagem.getStatus() > 2) {
			throw new Exception("Sou a DLQ e não consumo essa mensagem!");
		}
	}
	
}
