// Paquete del servicio de negocio para Product.
package com.crud.users_crud.service;

// Importa la entidad Product.
import com.crud.users_crud.entity.Product;
// Importa el repositorio para acceso a BD.
import com.crud.users_crud.repository.ProductRepository;
// Importa Lombok para inyeccion por constructor.
import lombok.RequiredArgsConstructor;
// Importa la anotacion de servicio.
import org.springframework.stereotype.Service;
// Importa la anotacion de transacciones.
import org.springframework.transaction.annotation.Transactional;

// Importa List para colecciones.
import java.util.List;

// Marca la clase como servicio de negocio.
@Service
// Genera constructor con dependencias final.
@RequiredArgsConstructor
public class ProductService {
    // Repositorio inyectado para operaciones CRUD.
    private final ProductRepository productRepository;

    // Abre transaccion para crear un producto.
    @Transactional
    public Product create(Product product) {

        // Validar que no exista un producto con el mismo nombre.
        if (productRepository.findByName(product.getName()).isPresent()) {
            throw new IllegalArgumentException("Ya existe el producto con el nombre: " + product.getName());
        }
        // Guarda la entidad y retorna la version persistida.
        return productRepository.save(product);
    }

    // Transaccion solo lectura para listar productos.
    @Transactional(readOnly = true)
    public List<Product> getAll() {
        // Delegar el SELECT al repositorio.
        return productRepository.findAll();
    }

    // Transaccion solo lectura para buscar por id.
    @Transactional(readOnly = true)
    public Product getById(Long id) {
        // Busca y si no existe lanza error.
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
    }

    // Transaccion para actualizar un producto existente.
    @Transactional
    public Product update(Long id, Product product) {

        // Verificar existencia antes de actualizar.
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));

        // Copiar campos actualizables desde la entrada.
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());

        // Guardar la entidad actualizada.
        return productRepository.save(existingProduct);
    }

    // Transaccion para eliminar por id.
    @Transactional
    public void deleteById(Long id) {
        // Validar existencia para devolver error claro.
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado con ID: " + id);
        }
        // Ejecutar DELETE en la BD.
        productRepository.deleteById(id);
    }


}
