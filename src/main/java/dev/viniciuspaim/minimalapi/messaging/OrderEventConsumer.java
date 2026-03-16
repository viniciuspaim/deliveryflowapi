package dev.viniciuspaim.minimalapi.messaging;

import dev.viniciuspaim.minimalapi.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
public class OrderEventConsumer {
    private static final Logger log = LoggerFactory.getLogger(OrderEventConsumer.class);

    @RabbitHandler
    public void handleOrderConfirmed(Long orderId) {
        log.info("Confirmed order received: {}", orderId);
    }
}
