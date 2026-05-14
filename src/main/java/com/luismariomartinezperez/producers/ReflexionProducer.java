package com.luismariomartinezperez.producers;

import com.luismariomartinezperez.dto.ReflexionRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReflexionProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${app.rabbitmq.exchange}")
    private String exchange;

    @Value("${app.rabbitmq.routing-key}")
    private String routingKey;

    public void sendReflexionEvent(ReflexionRequest request) {
        log.info("Enviando mensaje a RabbitMQ para el estudiante: {}", request.nombreCompleto());
        rabbitTemplate.convertAndSend(exchange, routingKey, request);
    }
}