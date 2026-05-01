package com.crud.users_crud.controller;

// Punto de entrada HTTP. Solo traduce peticiones a llamadas del Service y respuestas del Service a HTTP responses.
// No debe contener lógica de negocio.

import com.crud.users_crud.entity.User;
import com.crud.users_crud.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController                  // (1) Combina @Controller + @ResponseBody
@RequestMapping("/api/users")    // (2) Prefijo base para todos los endpoints de este controller
@RequiredArgsConstructor         // (3) Inyección por constructor vía Lombok
public class UserController {

    private final UserService userService;

    /**
     * POST /api/users
     * Crea un nuevo usuario.
     * Retorna 201 Created con el usuario creado en el body.
     */
    @PostMapping                          // (4) Mapea HTTP POST a este método
    public ResponseEntity<User> create(@RequestBody User user) { // (5) @RequestBody deserializa JSON → objeto
        User created = userService.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(created); // HTTP 201
    }

    /**
     * GET /api/users
     * Retorna todos los usuarios.
     * Retorna 200 OK con lista en el body.
     */
    @GetMapping                           // (6) Mapea HTTP GET a este método
    public ResponseEntity<List<User>> getAll() {
        List<User> users = userService.getAll();
        return ResponseEntity.ok(users); // HTTP 200
    }

    /**
     * GET /api/users/{id}
     * Retorna un usuario por su ID.
     * Retorna 200 OK si existe, lanza excepción si no.
     */
    @GetMapping("/{id}")                  // (7) {id} es una variable de path
    public ResponseEntity<User> getById(@PathVariable Long id) { // (8) @PathVariable extrae el valor del path
        User user = userService.getById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * PUT /api/users/{id}
     * Actualiza completamente un usuario existente.
     * Retorna 200 OK con el usuario actualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        User updated = userService.update(id, user);
        return ResponseEntity.ok(updated);
    }

    /**
     * DELETE /api/users/{id}
     * Elimina un usuario por su ID.
     * Retorna 204 No Content (eliminación exitosa sin body).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build(); // HTTP 204
    }
}