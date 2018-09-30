package com.rabbitmq.rabbitmqdemo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer {

	private final RabbitTemplate rabbitTemplate;
	
    private static final String EXCHANGE_ABASTECIMENTO = "fanout.abastecimento";
    
    @Autowired
    public Producer(final RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void novoAbastecimento(String bomba, Integer id, Double valor) {
		CustomMessage message = new CustomMessage();
		message.setBomba(bomba);
		message.setId(id);
		message.setTipo(TipoOperacao.INCLUIR);
		message.setValor(valor);
    	rabbitTemplate.convertAndSend(EXCHANGE_ABASTECIMENTO, "", message);
	}
	
    public void novoPagamento(Integer id) {
		CustomMessage message = new CustomMessage();
		message.setId(id);
		message.setTipo(TipoOperacao.RETIRAR);
		rabbitTemplate.convertAndSend(EXCHANGE_ABASTECIMENTO, "", message);
	}
}
