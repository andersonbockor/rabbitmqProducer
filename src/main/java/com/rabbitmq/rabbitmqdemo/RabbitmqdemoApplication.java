package com.rabbitmq.rabbitmqdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitmqdemoApplication implements CommandLineRunner{
	
	@Autowired
	private Producer producer;
	
	public static void main(String[] args) {
		SpringApplication.run(RabbitmqdemoApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub
		
		producer.novoAbastecimento("1", 1, 100.00); //Concentrador envia
		producer.novoAbastecimento("2", 2, 50.00); //Concentrador envia
		producer.novoPagamento(1); //PDV que recebeu envia
		producer.novoAbastecimento("1", 3, 100.00); //Concentrador envia
		producer.novoPagamento(2); //PDV que recebeu envia
		
	}

}
