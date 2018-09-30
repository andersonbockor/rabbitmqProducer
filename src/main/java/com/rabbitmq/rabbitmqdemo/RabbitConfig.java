package com.rabbitmq.rabbitmqdemo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

	private static final String QUEUE_PDV = "PDV";
	private static final String EXCHANGE_ABASTECIMENTO = "fanout.abastecimento";

	public List<String> pdvs() {
		List<String> pdvs = new ArrayList<String>();
		pdvs.add("1");
		pdvs.add("2");
		pdvs.add("3");
		return pdvs;
	}

	@Bean
	public List<Queue> queues() {
		List<Queue> queues = new ArrayList<Queue>();
		for (String pdv : pdvs()) {
			queues.add(QueueBuilder.durable(QUEUE_PDV.concat(pdv)).build());
		}
		return queues;
	}

	@Bean
	Exchange exchange() {
		// return ExchangeBuilder.topicExchange(EXCHANGE_ABASTECIMENTO).durable(true).build();
		return ExchangeBuilder.fanoutExchange(EXCHANGE_ABASTECIMENTO).durable(true).build();
	}

	/*@Bean
	List<Binding> queuesBinding() {
		List<Binding> queuesBinding = new ArrayList<Binding>();

		for (Queue queue : queues()) {
			queuesBinding.add(BindingBuilder.bind(queue).to(exchange()).with("Bico1").noargs());
			queuesBinding.add(BindingBuilder.bind(queue).to(exchange()).with("Bico2").noargs());
		}

		return queuesBinding;
	}*/
	
	@Bean
	List<Binding> queuesBinding() {
		List<Binding> queuesBinding = new ArrayList<Binding>();

		for (Queue queue : queues()) {
			queuesBinding.add(BindingBuilder.bind(queue).to(exchange()).with("informacoes-abastecimentos").noargs());
		}

		return queuesBinding;
	}
	
	//Outra forma de conversão
	/*@Bean
	public MessageConverter jsonMessageConverter(){
	    return new Jackson2JsonMessageConverter();
	}*/
	
	@Bean
	public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
	    return new Jackson2JsonMessageConverter();
	}
	
	public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
	    final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
	    rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
	    return rabbitTemplate;
	}

	@Bean
	ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
		connectionFactory.setUsername("guest");
		connectionFactory.setPassword("guest");
		return connectionFactory;
	}

}
