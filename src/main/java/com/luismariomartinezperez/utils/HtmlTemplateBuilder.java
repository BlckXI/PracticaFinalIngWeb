package com.luismariomartinezperez.utils;

public class HtmlTemplateBuilder {

    public static String buildModernEmail(String nombreCompleto) {
        return """
        <!DOCTYPE html>
        <html>
        <head>
            <style>
                body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f4f4f4; margin: 0; padding: 20px; }
                .container { background-color: #ffffff; padding: 30px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); max-width: 600px; margin: auto; }
                h2 { color: #333333; }
                p { color: #555555; line-height: 1.6; }
                .footer { margin-top: 20px; font-size: 12px; color: #aaaaaa; text-align: center; border-top: 1px solid #eeeeee; padding-top: 10px; }
            </style>
        </head>
        <body>
            <div class="container">
                <h2>Práctica Final Procesada</h2>
                <p>Hola,</p>
                <p>Se ha procesado exitosamente la reflexión de <strong>%s</strong>.</p>
                <p>En este correo se adjuntan los archivos generados en formato TXT y su versión comprimida en ZIP para respaldo.</p>
                <div class="footer">
                    &copy; 2026 ColibriHub - Sistema de Procesamiento Asíncrono
                </div>
            </div>
        </body>
        </html>
        """.formatted(nombreCompleto);
    }
}