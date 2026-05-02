package com.crud.users_crud.repository;

import com.crud.users_crud.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    //Crear producto evitando duplicados
    Optional<Product> findByName(String name);
}
