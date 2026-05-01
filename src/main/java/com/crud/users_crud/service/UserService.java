package com.crud.users_crud.service;

// Aquí vive toda la lógica de negocio: validaciones, transformaciones, orquestación.
// Nunca llama directamente a otro Controller; solo usa Repositories.

import com.crud.users_crud.entity.User;
import com.crud.users_crud.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service                  // (1) Marca este bean como servicio de negocio
@RequiredArgsConstructor  // (2) Lombok: genera constructor con campos final (inyección por constructor)
public class UserService {

    private final UserRepository userRepository; // (3) Inyección por constructor (mejor práctica)

    /**
     * Crea un nuevo usuario.
     * Valida que no exista otro usuario con el mismo email antes de persistir.
     *
     * @param user entidad a persistir
     * @return entidad persistida con ID generado
     */
    @Transactional // (4) Envuelve la operación en una transacción de BD
    public User create(User user) {
        // Validación de negocio: email único
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un usuario con el email: " + user.getEmail());
        }
        return userRepository.save(user);
    }

    /**
     * Retorna todos los usuarios de la base de datos.
     *
     * @return lista de usuarios (vacía si no hay ninguno)
     */
    @Transactional(readOnly = true) // (5) Optimización: transacción de solo lectura
    public List<User> getAll() {
        return userRepository.findAll();
    }

    /**
     * Busca un usuario por su ID.
     * Lanza excepción si no existe (fail-fast pattern).
     *
     * @param id identificador del usuario
     * @return usuario encontrado
     */
    @Transactional(readOnly = true)
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    /**
     * Actualiza los datos de un usuario existente.
     * Primero verifica existencia, luego actualiza.
     *
     * @param id   identificador del usuario a actualizar
     * @param user datos nuevos
     * @return usuario actualizado
     */
    @Transactional
    public User update(Long id, User user) {
        // Verificar existencia antes de actualizar
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        // Actualizar solo los campos permitidos (evita sobrescribir el ID)
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setAge(user.getAge());

        // save() en entidad ya persistida ejecuta UPDATE, no INSERT
        return userRepository.save(existingUser);
    }

    /**
     * Elimina un usuario por su ID.
     * Verifica existencia para lanzar error informativo si no existe.
     *
     * @param id identificador del usuario a eliminar
     */
    @Transactional
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }
        userRepository.deleteById(id);
    }
}