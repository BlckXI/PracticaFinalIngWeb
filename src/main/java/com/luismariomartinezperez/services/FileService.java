package com.luismariomartinezperez.services;

import com.luismariomartinezperez.dto.ReflexionRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@Service
public class FileService {

    @Value("${app.storage.path}")
    private String storagePath;

    public File[] generateFilesAndZip(ReflexionRequest request) throws IOException {
        Path storageDir = Paths.get(storagePath);
        if (!Files.exists(storageDir)) {
            Files.createDirectories(storageDir);
        }

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String baseFileName = request.cif() + "_" + timestamp;

        File txtFile = storageDir.resolve(baseFileName + ".txt").toFile();
        File zipFile = storageDir.resolve(baseFileName + ".zip").toFile();

        // 1. Escribir TXT
        try (PrintWriter writer = new PrintWriter(new FileWriter(txtFile))) {
            writer.println("CIF: " + request.cif());
            writer.println("Nombre Completo: " + request.nombreCompleto());
            writer.println("Correo: " + request.correo());
            writer.println("Fecha y Hora: " + LocalDateTime.now());
            writer.println("Mensaje de Reflexión:\n" + request.mensajeReflexion());
        }
        log.info("Archivo TXT generado localmente: {}", txtFile.getAbsolutePath());

        // 2. Comprimir a ZIP
        try (FileOutputStream fos = new FileOutputStream(zipFile);
             ZipOutputStream zos = new ZipOutputStream(fos);
             FileInputStream fis = new FileInputStream(txtFile)) {

            ZipEntry zipEntry = new ZipEntry(txtFile.getName());
            zos.putNextEntry(zipEntry);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) >= 0) {
                zos.write(buffer, 0, length);
            }
            zos.closeEntry();
        }
        log.info("Archivo ZIP generado localmente: {}", zipFile.getAbsolutePath());

        return new File[]{txtFile, zipFile};
    }
}