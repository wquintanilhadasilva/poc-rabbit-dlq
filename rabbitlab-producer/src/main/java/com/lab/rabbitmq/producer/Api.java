package com.lab.rabbitmq.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/message")
public class Api {
	
	private final MessageService messageService;
	
	@PostMapping
	public ResponseEntity<Void> sendMessage(@RequestBody Mensagem mensagem) {
		messageService.sendMessage(mensagem);
		return  ResponseEntity.accepted().build();
	}

}
