package dev.viniciuspaim.deliveryflowapi.order.messaging;


import dev.viniciuspaim.deliveryflowapi.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderEventProducer {

    private static final Logger log = LoggerFactory.getLogger(OrderEventProducer.class);
    private final RabbitTemplate rabbitTemplate;

    // RabbitTemplate é injetado automaticamente pelo Spring
    public OrderEventProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendOrderConfirmedEvent(Long orderId) {
        // Envia para uma exchange específica com uma routing key
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, orderId);
        log.info("Mensagem enviada: {}", orderId);
    }
}
