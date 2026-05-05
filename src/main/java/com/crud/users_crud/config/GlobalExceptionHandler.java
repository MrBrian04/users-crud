// Paquete de configuración centralizada de manejo de excepciones.
package com.crud.users_crud.config;

// Importa anotacion para marcar esta clase como manejador global de excepciones.
import org.springframework.web.bind.annotation.ExceptionHandler;
// Importa anotacion para REST controllers.
import org.springframework.web.bind.annotation.RestControllerAdvice;
// Importa ResponseEntity para construir respuestas HTTP.
import org.springframework.http.ResponseEntity;
// Importa HttpStatus para códigos de respuesta.
import org.springframework.http.HttpStatus;
// Importa excepción de validación de Spring.
import org.springframework.web.bind.MethodArgumentNotValidException;
// Importa stream para procesar errores de validación.
import java.util.stream.Collectors;

// Anotacion que marca esta clase como manejador GLOBAL de excepciones para todos los controllers REST.
// Cualquier excepción que se lance en los controllers será interceptada e procesada aqui.
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja excepciones de tipo RuntimeException (incluye errores de lógica de negocio).
     * Retorna 404 Not Found si es error de búsqueda o 400 Bad Request si es validación.
     *
     * @param ex la excepción capturada
     * @return respuesta HTTP con código de estado y mensaje de error
     */
    // Detecta cuando se lanza una RuntimeException en cualquier controller.
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        // Verifica si el mensaje contiene "no encontrado" para devolver 404.
        if (ex.getMessage().toLowerCase().contains("no encontrado")) {
            // 404 Not Found: recurso solicitado no existe.
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
        // Si es otro tipo de RuntimeException, devuelve 500 Internal Server Error.
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    /**
     * Maneja excepciones de validación de anotaciones (IllegalArgumentException).
     * Retorna 400 Bad Request con el mensaje de validación.
     *
     * @param ex la excepción capturada
     * @return respuesta HTTP 400 con mensaje
     */
    // Detecta cuando se lanza una IllegalArgumentException (validaciones fallidas).
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        // 400 Bad Request: datos inválidos o conflicto de negocio.
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * Maneja excepciones de validación de anotaciones (@NotNull, @Email, @Size, etc).
     * Agregrega todos los errores de validación y devuelve 400 con los mensajes.
     *
     * @param ex la excepción capturada con los errores de validación
     * @return respuesta HTTP 400 con lista de errores
     */
    // Detecta cuando Spring valida anotaciones y fallan (@NotNull, @Email, @Size, etc).
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        // Extrae TODOS los errores de validación en un String legible.
        // Ejemplo: "name: El nombre no puede estar vacío; email: El email debe ser válido"
        String errors = ex.getBindingResult()
                .getFieldErrors()  // Obtiene todos los errores de campos
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())  // Formatea cada error
                .collect(Collectors.joining("; "));  // Junta todos con "; "

        // 400 Bad Request: datos no cumplen validaciones de anotaciones.
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}

