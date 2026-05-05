// Paquete de repositorios JPA.
package com.crud.users_crud.repository;

// Interface que extiende JpaRepository.
// Spring Data genera automaticamente la implementacion en runtime. No escribes SQL para operaciones basicas.

// Importa la entidad User que este repositorio maneja.
import com.crud.users_crud.entity.User;
// Importa la base de repositorios CRUD de Spring Data JPA.
import org.springframework.data.jpa.repository.JpaRepository;
// Importa la anotacion de repositorio.
import org.springframework.stereotype.Repository;

// Importa Optional para resultados que pueden no existir.
import java.util.Optional;

// Marca este bean como repositorio y habilita traduccion de excepciones.
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Spring Data genera el SQL automaticamente por convencion de nombres.
    Optional<User> findByEmail(String email);

    // JpaRepository ya te hereda estos metodos sin que los escribas:
    // save(entity)        -> INSERT o UPDATE
    // findById(id)        -> SELECT por ID
    // findAll()           -> SELECT todos
    // deleteById(id)      -> DELETE por ID
    // existsById(id)      -> SELECT COUNT
    // count()             -> SELECT COUNT(*)
}