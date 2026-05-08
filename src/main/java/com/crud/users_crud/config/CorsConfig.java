// Paquete de configuración centralizada.
package com.crud.users_crud.config;

// Importa anotación @Configuration que marca una clase como configuración de Spring.
import org.springframework.context.annotation.Configuration;
// Importa @Bean para definir beans (objetos que Spring gestiona).
import org.springframework.context.annotation.Bean;
// Importa WebMvcConfigurer para personalizar configuración web.
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
// Importa CorsRegistry para configurar CORS (Cross-Origin Resource Sharing).
import org.springframework.web.servlet.config.annotation.CorsRegistry;

/**
 * Configuración CORS (Cross-Origin Resource Sharing) para permitir que aplicaciones
 * en otros orígenes (ej: React en localhost:3000) accedan a esta API.
 *
 * Sin CORS: El browser bloquearía las peticiones de React a este backend.
 * Con CORS: Spring autoriza explícitamente qué orígenes pueden acceder.
 */
// Anotación que marca esta clase como configuración de Spring Boot.
// Se ejecuta automáticamente al iniciar la aplicación.
@Configuration
public class CorsConfig {

    /**
     * Define un bean WebMvcConfigurer que personaliza la configuración MVC de Spring.
     *
     * MVC = Model-View-Controller (arquitectura web).
     * @Bean = Spring crea una instancia única de este objeto y lo gestiona.
     *
     * @return configurador de CORS/MVC que Spring aplicará a todos los endpoints.
     */
    // Anotación @Bean: Spring crea una instancia de WebMvcConfigurer y la gestiona.
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        // Retorna una implementación anónima de WebMvcConfigurer.
        // Usamos clase anónima para personalizar el comportamiento de CORS.
        return new WebMvcConfigurer() {

            /**
             * Método que sobrescribimos de WebMvcConfigurer.
             * Se ejecuta durante la inicialización de Spring para configurar CORS.
             *
             * @param registry el registro donde agregaremos la configuración CORS.
             */
            // @Override = estamos sobrescribiendo el método de la clase padre.
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // registry = objeto que gestiona todas las configuraciones CORS.

                /**
                 * registry.addMapping("/api/**")
                 * QUIÉN: Aplica configuración CORS a todos los endpoints que empiezan con /api
                 * /api/** = patrón que significa "todos los paths bajo /api"
                 * Ejemplos:
                 *   - /api/users
                 *   - /api/users/1
                 *   - /api/products
                 *   - /api/categories/5
                 */
                registry.addMapping("/api/**")

                    /**
                     * .allowedOrigins("http://localhost:3000")
                     * QUÉ ORIGEN ESTÁ PERMITIDO: Solo React (en localhost:3000) puede acceder.
                     *
                     * localhost:3000 = tu app React en desarrollo.
                     * Si hubiera múltiples orígenes: .allowedOrigins("http://localhost:3000", "http://localhost:3001")
                     *
                     * ⚠️ NUNCA usar "*" en producción (abre la API a cualquiera).
                     */
                    .allowedOrigins("http://localhost:3000")

                    /**
                     * .allowedMethods("GET", "POST", "PUT", "DELETE")
                     * QUÉ VERBOS HTTP ESTÁN PERMITIDOS: Solo estos 4 métodos.
                     *
                     * GET     = obtener datos (ej: GET /api/users/1)
                     * POST    = crear datos (ej: POST /api/users)
                     * PUT     = actualizar datos (ej: PUT /api/users/1)
                     * DELETE  = eliminar datos (ej: DELETE /api/users/1)
                     *
                     * Si necesitamos PATCH (actualización parcial), lo agregamos aquí.
                     */
                    .allowedMethods("GET", "POST", "PUT", "DELETE")

                    /**
                     * .allowedHeaders("*")
                     * QUÉ HEADERS HTTP ESTÁN PERMITIDOS: Todos ("*" = wildcard = cualquiera).
                     *
                     * Headers comunes:
                     *   - Content-Type (tipo del body: application/json)
                     *   - Authorization (tokens JWT)
                     *   - Accept (formato esperado en respuesta)
                     *
                     * "*" = se permiten ALL headers. Alternativa explícita:
                     * .allowedHeaders("Content-Type", "Authorization", "Accept")
                     */
                    .allowedHeaders("*");

                // Después de esta línea, Spring ha configurado CORS.
                // Cualquier petición desde http://localhost:3000 será autorizada.
            }
        };
    }

    /**
     * EXPLICACIÓN GENERAL:
     *
     * 1) React hace: fetch("http://localhost:8080/api/users")
     *
     * 2) Browser ve: "Orígenes diferentes" (localhost:3000 vs localhost:8080)
     *    → Browser hace preflight: OPTIONS request a /api/users
     *
     * 3) Spring ve el OPTIONS request
     *    → Consulta CorsConfig
     *    → Verifica: ¿localhost:3000 está en allowedOrigins? SÍ ✅
     *    → Verifica: ¿El método es GET/POST/PUT/DELETE? SÍ ✅
     *    → Envía response: "Sí, está permitido"
     *
     * 4) Browser recibe respuesta OK
     *    → Permite que React haga el fetch real
     *
     * 5) Resultado: ✅ React recibe datos de la API
     */
}

