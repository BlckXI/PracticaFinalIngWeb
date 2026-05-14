package com.luismariomartinezperez.services;

import com.luismariomartinezperez.dto.ReflexionRequest;
import com.luismariomartinezperez.utils.HtmlTemplateBuilder;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.email.from}")
    private String fromEmail;

    @Value("${app.email.target}")
    private String defaultTargetEmail;

    public void sendReflexionEmail(ReflexionRequest request, File txtFile, File zipFile) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            // El 'true' indica que es un mensaje multipart (para adjuntos y HTML)
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            String subject = "practica-final | " + request.nombreCompleto().trim().toLowerCase().replaceAll("\\s+", "-");
            String htmlBody = HtmlTemplateBuilder.buildModernEmail(request.nombreCompleto());

            helper.setFrom(fromEmail);
            // Enviamos el correo a la dirección destino por defecto y al correo del estudiante
            helper.setTo(new String[]{defaultTargetEmail, request.correo()});
            helper.setSubject(subject);
            helper.setText(htmlBody, true); // true = habilita el HTML

            // Adjuntar los archivos locales
            helper.addAttachment(txtFile.getName(), txtFile);
            helper.addAttachment(zipFile.getName(), zipFile);

            log.info("Enviando correo vía SMTP a: {} y {}", defaultTargetEmail, request.correo());
            mailSender.send(message);
            log.info("Correo enviado exitosamente.");

        } catch (Exception e) {
            log.error("Ocurrió un error al enviar el correo SMTP: {}", e.getMessage(), e);
        }
    }
}