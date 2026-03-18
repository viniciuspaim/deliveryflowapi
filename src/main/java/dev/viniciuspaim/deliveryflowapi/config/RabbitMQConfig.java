package dev.viniciuspaim.deliveryflowapi.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "queue1";
    public static final String EXCHANGE_NAME = "exchange1";
    public static final String ROUTING_KEY = "order.confirmed";

    @Bean
    public Queue queue1() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public TopicExchange exchange1() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding binding1(Queue queue1, TopicExchange exchange1) {
        return BindingBuilder.bind(queue1).to(exchange1).with(ROUTING_KEY);
    }
}
