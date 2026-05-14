package com.luismariomartinezperez.consumers;

import com.luismariomartinezperez.dto.ReflexionRequest;
import com.luismariomartinezperez.services.FileService;
import com.luismariomartinezperez.services.EmailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.File;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReflexionConsumer {

    private final FileService fileService;
    private final EmailService emailService;

    @RabbitListener(queues = "${app.rabbitmq.queue}")
    public void consumeReflexionEvent(ReflexionRequest request) {
        log.info("Iniciando procesamiento de consumidor RabbitMQ para CIF: {}", request.cif());
        try {
            // 1. Generar TXT y comprimir a ZIP
            File[] files = fileService.generateFilesAndZip(request);
            File txtFile = files[0];
            File zipFile = files[1];

            // 2. Enviar Correo con Adjuntos
            emailService.sendReflexionEmail(request, txtFile, zipFile);

            log.info("Procesamiento finalizado con éxito para CIF: {}", request.cif());
        } catch (Exception e) {
            log.error("Error procesando el mensaje para CIF: {}. Causa: {}", request.cif(), e.getMessage(), e);
        }
    }
}