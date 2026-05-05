// Paquete del servicio de negocio para User.
package com.crud.users_crud.service;

// Aqui vive toda la logica de negocio: validaciones, transformaciones, orquestacion.
// Nunca llama directamente a otro Controller; solo usa Repositories.

// Importa la entidad User que se persiste.
import com.crud.users_crud.entity.User;
// Importa el repositorio que accede a la base de datos.
import com.crud.users_crud.repository.UserRepository;
// Importa Lombok para generar constructor con dependencias final.
import lombok.RequiredArgsConstructor;
// Importa la anotacion de servicio de Spring.
import org.springframework.stereotype.Service;
// Importa la anotacion de transacciones.
import org.springframework.transaction.annotation.Transactional;

// Importa List para colecciones.
import java.util.List;

// Marca esta clase como servicio de negocio y bean de Spring.
@Service
// Genera constructor con campos final para inyeccion por constructor.
@RequiredArgsConstructor
public class UserService {

    // Repositorio inyectado por constructor (mejor practica).
    private final UserRepository userRepository;

    /**
     * Crea un nuevo usuario.
     * Valida que no exista otro usuario con el mismo email antes de persistir.
     *
     * @param user entidad a persistir
     * @return entidad persistida con ID generado
     */
    // Abre una transaccion de BD para que la operacion sea atomica.
    @Transactional
    public User create(User user) {
        // Validacion de negocio: email unico.
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            // Si existe, se corta el flujo con una excepcion clara.
            throw new IllegalArgumentException("Ya existe un usuario con el email: " + user.getEmail());
        }
        // Guarda el usuario; JPA decide INSERT y devuelve la entidad con ID.
        return userRepository.save(user);
    }

    /**
     * Retorna todos los usuarios de la base de datos.
     *
     * @return lista de usuarios (vacia si no hay ninguno)
     */
    // Marca la transaccion como solo lectura para optimizar el acceso.
    @Transactional(readOnly = true)
    public List<User> getAll() {
        // Delegar el SELECT al repositorio.
        return userRepository.findAll();
    }

    /**
     * Busca un usuario por su ID.
     * Lanza excepcion si no existe (fail-fast pattern).
     *
     * @param id identificador del usuario
     * @return usuario encontrado
     */
    // Solo lectura porque no modifica datos.
    @Transactional(readOnly = true)
    public User getById(Long id) {
        // Busca por id y si no existe lanza error.
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
    // Abre transaccion porque hay lectura + escritura.
    @Transactional
    public User update(Long id, User user) {
        // Verificar existencia antes de actualizar.
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        // Actualizar solo los campos permitidos (evita sobrescribir el ID).
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setAge(user.getAge());

        // save() en entidad ya persistida ejecuta UPDATE, no INSERT.
        return userRepository.save(existingUser);
    }

    /**
     * Elimina un usuario por su ID.
     * Verifica existencia para lanzar error informativo si no existe.
     *
     * @param id identificador del usuario a eliminar
     */
    // Abre transaccion para la eliminacion.
    @Transactional
    public void delete(Long id) {
        // Validar existencia para dar un error explicito.
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }
        // Ejecuta DELETE en la BD.
        userRepository.deleteById(id);
    }
}