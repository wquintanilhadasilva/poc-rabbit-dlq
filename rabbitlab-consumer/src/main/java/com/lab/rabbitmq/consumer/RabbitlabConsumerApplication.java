package com.lab.rabbitmq.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class RabbitlabConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbitlabConsumerApplication.class, args);
	}

}
