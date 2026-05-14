package com.luismariomartinezperez.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ReflexionRequest(
        @NotBlank(message = "El CIF es obligatorio") String cif,
        @NotBlank(message = "El nombre completo es obligatorio") String nombreCompleto,
        @Email(message = "Debe ser un correo válido") @NotBlank String correo,
        @NotBlank(message = "El mensaje de reflexión es obligatorio") String mensajeReflexion
) {}