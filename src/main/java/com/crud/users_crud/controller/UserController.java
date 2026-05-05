// Paquete del controlador HTTP para el recurso User.
package com.crud.users_crud.controller;

// Punto de entrada HTTP. Solo traduce peticiones a llamadas del Service y respuestas del Service a HTTP responses.
// No debe contener logica de negocio.

// Importa la entidad User que viaja en el body y en respuestas.
import com.crud.users_crud.entity.User;
// Importa el servicio que contiene la logica de negocio.
import com.crud.users_crud.service.UserService;
// Importa Lombok para generar el constructor con dependencias finales.
import lombok.RequiredArgsConstructor;
// Importa anotación @Valid para validar entrada.
import jakarta.validation.Valid;
// Importa enums de estado HTTP.
import org.springframework.http.HttpStatus;
// Importa ResponseEntity para controlar status y body.
import org.springframework.http.ResponseEntity;
// Importa anotaciones de mapeo HTTP.
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Importa List para colecciones de respuestas.
import java.util.List;

// Marca la clase como controlador REST (devuelve JSON por defecto).
@RestController
// Define el prefijo base para todos los endpoints de este controller.
@RequestMapping("/api/users")
// Genera constructor con todas las dependencias final (inyeccion por constructor).
@RequiredArgsConstructor
public class UserController {

    // Dependencia al servicio: el controller delega la logica aqui.
    private final UserService userService;

    /**
     * POST /api/users
     * Crea un nuevo usuario.
     * Retorna 201 Created con el usuario creado en el body.
     */
    // Mapea HTTP POST a este metodo.
    @PostMapping
    // @Valid: Valida el user antes de pasarlo al servicio usando anotaciones (@NotNull, @Email, etc).
    // Si falla, GlobalExceptionHandler.handleMethodArgumentNotValid() devuelve 400 con errores.
    public ResponseEntity<User> create(@Valid @RequestBody User user) {
        // Llama al servicio para crear y validar la entidad.
        User created = userService.create(user);
        // Responde 201 Created con el usuario persistido.
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * GET /api/users
     * Retorna todos los usuarios.
     * Retorna 200 OK con lista en el body.
     */
    // Mapea HTTP GET sin path adicional.
    @GetMapping
    // Devuelve una lista completa de usuarios.
    public ResponseEntity<List<User>> getAll() {
        // Pide al servicio la lista completa.
        List<User> users = userService.getAll();
        // Responde 200 OK con la lista.
        return ResponseEntity.ok(users);
    }

    /**
     * GET /api/users/{id}
     * Retorna un usuario por su ID.
     * Retorna 200 OK si existe, lanza excepcion si no.
     */
    // Mapea HTTP GET con variable de path.
    @GetMapping("/{id}")
    // Extrae el id desde la URL y lo pasa al servicio.
    public ResponseEntity<User> getById(@PathVariable Long id) {
        // Busca el usuario por id.
        User user = userService.getById(id);
        // Responde 200 OK con el usuario.
        return ResponseEntity.ok(user);
    }

    /**
     * PUT /api/users/{id}
     * Actualiza completamente un usuario existente.
     * Retorna 200 OK con el usuario actualizado.
     */
    // Mapea HTTP PUT con variable de path.
    @PutMapping("/{id}")
    // Recibe el id desde la URL y el JSON desde el body.
    // @Valid: Valida el user actualizado antes de pasarlo al servicio.
    public ResponseEntity<User> update(@PathVariable Long id, @Valid @RequestBody User user) {
        // Delegar la actualizacion al servicio.
        User updated = userService.update(id, user);
        // Responde 200 OK con el usuario actualizado.
        return ResponseEntity.ok(updated);
    }

    /**
     * DELETE /api/users/{id}
     * Elimina un usuario por su ID.
     * Si no existe: GlobalExceptionHandler captura la excepcion y devuelve 404 con mensaje.
     * Si existe: devuelve 204 No Content (exito sin body).
     */
    // Mapea HTTP DELETE con variable de path.
    @DeleteMapping("/{id}")
    // Recibe el id desde la URL.
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // Solicita al servicio borrar el recurso.
        // Si el usuario NO existe, el servicio lanza RuntimeException("Usuario no encontrado...").
        // El GlobalExceptionHandler intercepta esa excepcion y devuelve 404 + mensaje de error.
        // Si TODO OK, la linea siguiente se ejecuta.
        userService.delete(id);
        // Responde 204 No Content SOLO si el usuario fue eliminado con exito (sin excepcion).
        return ResponseEntity.noContent().build();
    }
}