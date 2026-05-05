// Paquete del controlador HTTP para el recurso Category.
package com.crud.users_crud.controller;

// Importa la entidad Category para el body y las respuestas.
import com.crud.users_crud.entity.Category;
// Importa el servicio con la logica de negocio.
import com.crud.users_crud.service.CategoryService;
// Importa Lombok para inyeccion por constructor.
import lombok.RequiredArgsConstructor;
// Importa anotación @Valid para validar entrada.
import jakarta.validation.Valid;
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

// Importa List para colecciones.
import java.util.List;

// Marca esta clase como controlador REST.
@RestController
// Define el prefijo base de endpoints para categorias.
@RequestMapping("/api/categories")
// Genera constructor con dependencias final.
@RequiredArgsConstructor
public class CategoryController {
    // Servicio de categorias inyectado por constructor.
    private final CategoryService categoryService;

    // Mapea POST /api/categories para crear una categoria.
    @PostMapping
    // @Valid: Valida la categoría antes de pasarla al servicio usando anotaciones.
    // Si falla, GlobalExceptionHandler devuelve 400 con errores de validación.
    public ResponseEntity<Category> create(@Valid @RequestBody Category category) {
        // Delegar creacion al servicio.
        Category created = categoryService.create(category);
        // Responder 201 Created con la categoria persistida.
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Mapea GET /api/categories para listar categorias.
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        // Consultar todas las categorias.
        List<Category> categories = categoryService.getAll();
        // Responder 200 OK con la lista.
        return ResponseEntity.ok(categories);
    }

    // Mapea GET /api/categories/{id} para obtener una categoria por id.
    @GetMapping("/{id}")
    public ResponseEntity<Category> getbyid(@PathVariable Long id) {
        // Buscar categoria por id en el servicio.
        Category category = categoryService.getById(id);
        // Responder 200 OK con la categoria.
        return ResponseEntity.ok(category);
    }

     // Mapea PUT /api/categories/{id} para actualizar una categoria.
     @PutMapping("/{id}")
     // @Valid: Valida la categoría actualizada antes de pasarla al servicio.
     public ResponseEntity<Category> update(@PathVariable Long id, @Valid @RequestBody Category category) {
         // Delegar actualizacion al servicio.
         Category updated = categoryService.update(id, category);
         // Responder 200 OK con la categoria actualizada.
         return ResponseEntity.ok(updated);
     }

    // Mapea DELETE /api/categories/{id} para eliminar una categoria.
    // Si no existe: GlobalExceptionHandler captura la excepcion y devuelve 404 con mensaje.
    // Si existe: devuelve 204 No Content (exito sin body).
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // Solicitar al servicio eliminar por id.
        // Si la categoria NO existe, el servicio lanza RuntimeException("Categoria no encontrada...").
        // El GlobalExceptionHandler intercepta esa excepcion y devuelve 404 + mensaje de error.
        // Si TODO OK, la linea siguiente se ejecuta.
        categoryService.delete(id);
        // Responder 204 No Content SOLO si la categoria fue eliminada con exito (sin excepcion).
        return ResponseEntity.noContent().build();
    }


}
