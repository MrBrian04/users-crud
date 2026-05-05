// Paquete de repositorios JPA.
package com.crud.users_crud.repository;

// Importa la entidad Product que este repositorio maneja.
import com.crud.users_crud.entity.Product;
// Importa la base de repositorios CRUD de Spring Data JPA.
import org.springframework.data.jpa.repository.JpaRepository;
// Importa la anotacion de repositorio.
import org.springframework.stereotype.Repository;

// Importa Optional para resultados que pueden no existir.
import java.util.Optional;

// Marca este bean como repositorio.
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Buscar por nombre para evitar duplicados.
    Optional<Product> findByName(String name);
}
