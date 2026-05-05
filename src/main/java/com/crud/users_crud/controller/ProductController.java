// Paquete del controlador HTTP para el recurso Product.
package com.crud.users_crud.controller;

// Importa la entidad Product para el body y las respuestas.
import com.crud.users_crud.entity.Product;
// Importa el servicio que contiene la logica de negocio de productos.
import com.crud.users_crud.service.ProductService;
// Importa Lombok para inyeccion por constructor.
import lombok.RequiredArgsConstructor;
// Importa estado HTTP y ResponseEntity.
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// Importa anotaciones REST.
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Importa List para colecciones de respuesta.
import java.util.List;

// Marca esta clase como controlador REST.
@RestController
// Define el prefijo base de endpoints para productos.
@RequestMapping("/api/products")
// Genera constructor con dependencias final.
@RequiredArgsConstructor
public class ProductController {

    // Servicio de productos inyectado por constructor.
    private final ProductService productService;

    // Mapea POST /api/products para crear un producto.
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        // Delegar creacion al servicio.
        Product created = productService.create(product);
        // Responder 201 Created con el producto persistido.
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Mapea GET /api/products para listar productos.
    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        // Consultar todos los productos.
        List<Product> products = productService.getAll();
        // Responder 200 OK con la lista.
        return ResponseEntity.ok(products);
    }

    // Mapea GET /api/products/{id} para obtener un producto por id.
    @GetMapping("/{id}")
    public ResponseEntity<Product> getByid(@PathVariable Long id) {
        // Buscar producto por id en el servicio.
        Product product = productService.getById(id);
        // Responder 200 OK con el producto.
        return ResponseEntity.ok(product);
    }

    // Mapea PUT /api/products/{id} para actualizar un producto.
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateByid(@PathVariable Long id, @RequestBody Product product) {
        // Delegar la actualizacion al servicio.
        Product updated = productService.update(id, product);
        // Responder 200 OK con el producto actualizado.
        return ResponseEntity.ok(updated);
    }

    // Mapea DELETE /api/products/{id} para eliminar un producto.
    // Si no existe: GlobalExceptionHandler captura la excepcion y devuelve 404 con mensaje.
    // Si existe: devuelve 204 No Content (exito sin body).
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteByid(@PathVariable Long id) {
        // Solicitar al servicio eliminar por id.
        // Si el producto NO existe, el servicio lanza RuntimeException("Producto no encontrado...").
        // El GlobalExceptionHandler intercepta esa excepcion y devuelve 404 + mensaje de error.
        // Si TODO OK, la linea siguiente se ejecuta.
        productService.deleteById(id);
        // Responder 204 No Content SOLO si el producto fue eliminado con exito (sin excepcion).
        return ResponseEntity.noContent().build();
    }


}
