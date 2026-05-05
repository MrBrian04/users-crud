// Paquete del servicio de negocio para Category.
package com.crud.users_crud.service;

// Importa la entidad Category.
import com.crud.users_crud.entity.Category;
// Importa el repositorio para acceso a BD.
import com.crud.users_crud.repository.CategoryRepository;
// Importa anotacion de transacciones.
import org.springframework.transaction.annotation.Transactional;
// Importa Lombok para inyeccion por constructor.
import lombok.RequiredArgsConstructor;
// Importa anotacion de servicio de Spring.
import org.springframework.stereotype.Service;

// Importa List para colecciones.
import java.util.List;

// Marca la clase como servicio de negocio.
@Service
// Genera constructor con dependencias final.
@RequiredArgsConstructor
public class CategoryService {

    // Repositorio inyectado para operaciones CRUD.
    private final CategoryRepository categoryRepository;

    // Abre transaccion para crear una categoria.
    @Transactional
    public Category create(Category category) {

        // Validar que no exista una categoria con el mismo nombre.
        if (categoryRepository.findByName(category.getName()).isPresent()) {
            throw new IllegalArgumentException("La categoria ya existe con el nombre: " + category.getName());
        }
        // Guarda la entidad y retorna la version persistida.
        return categoryRepository.save(category);
    }

    // Transaccion solo lectura para listar categorias.
    @Transactional(readOnly = true)
    public List<Category> getAll() {
        // Delegar el SELECT al repositorio.
        return categoryRepository.findAll();
    }

    // Transaccion solo lectura para buscar por id.
    @Transactional(readOnly = true)
    public Category getById(Long id) {
        // Busca y si no existe lanza error.
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada con ID: " + id));
    }

    // Transaccion para actualizar una categoria.
    @Transactional
    public Category update(Long id, Category category) {

        // Verificar existencia antes de actualizar.
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada con ID: " + id));

        // Copiar campos actualizables desde la entrada.
        existingCategory.setName(category.getName());
        existingCategory.setDescription(category.getDescription());
        // Guardar la entidad actualizada.
        return categoryRepository.save(existingCategory);
    }

    // Transaccion para eliminar por id.
    @Transactional
    public void delete(Long id) {
        // Validar existencia para devolver error claro.
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Categoria no encontrada con ID: " + id);
        }
        // Ejecutar DELETE en la BD.
        categoryRepository.deleteById(id);
    }


}

