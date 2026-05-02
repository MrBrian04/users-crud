package com.crud.users_crud.service;

import com.crud.users_crud.entity.Product;
import com.crud.users_crud.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional // (4) Envuelve la operación en una transacción de BD
    public Product create (Product product) {

        if (productRepository.findByName(product.getName()).isPresent()) {
            throw new IllegalArgumentException("Ya existe el producto con el nombre: " + product.getName());
        }
        return productRepository.save(product);
    }

    @Transactional(readOnly = true) // readOnly true, solo consulta datos sin modificarlas
    public List<Product> getAll() {return productRepository.findAll();}

    @Transactional(readOnly = true)
    public Product getById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
    }

    public Product update(Long id, Product product) {

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));

        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());

        return productRepository.save(existingProduct);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado con ID: " + id);
        }
        productRepository.deleteById(id);
    }


}
