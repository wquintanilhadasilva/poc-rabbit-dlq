package com.lab.rabbitmq.consumer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class Mensagem {
	private String mensagem;
	private LocalDateTime time;
	private Integer status;
}
