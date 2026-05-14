package com.luismariomartinezperez.controllers;

import com.luismariomartinezperez.dto.ReflexionRequest;
import com.luismariomartinezperez.producers.ReflexionProducer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/reflexiones")
@RequiredArgsConstructor
public class ReflexionController {

    private final ReflexionProducer reflexionProducer;

    @PostMapping
    public ResponseEntity<String> procesarReflexion(@Valid @RequestBody ReflexionRequest request) {
        log.info("Recibida solicitud HTTP POST /api/reflexiones para CIF: {}", request.cif());
        reflexionProducer.sendReflexionEvent(request);
        return ResponseEntity.accepted().body("Solicitud recibida y encolada para procesamiento asíncrono.");
    }
}