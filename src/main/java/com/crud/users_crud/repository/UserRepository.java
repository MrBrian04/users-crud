package com.crud.users_crud.repository;

// Interface que extiende JpaRepository.
// Spring Data genera automáticamente la implementación en runtime. No escribes SQL para operaciones básicas.

import com.crud.users_crud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository  // (1) Marca este bean como repositorio; activa traducción de excepciones de BD
public interface UserRepository extends JpaRepository<User, Long> {

    // (2) Spring Data genera el SQL automáticamente por convención de nombres
    Optional<User> findByEmail(String email);

    // JpaRepository ya te hereda estos métodos sin que los escribas:
    // save(entity)        → INSERT o UPDATE
    // findById(id)        → SELECT por ID
    // findAll()           → SELECT todos
    // deleteById(id)      → DELETE por ID
    // existsById(id)      → SELECT COUNT
    // count()             → SELECT COUNT(*)
}