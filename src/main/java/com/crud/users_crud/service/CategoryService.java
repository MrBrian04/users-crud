package com.crud.users_crud.service;

import com.crud.users_crud.entity.Category;
import com.crud.users_crud.repository.CategoryRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public Category create (Category category){

        if (categoryRepository.findByName(category.getName()).isPresent()){
            throw new IllegalArgumentException("La categoría ya existe con el nombre: " + category.getName());
        }
        return categoryRepository.save(category);
    }

    @Transactional(readOnly = true)
    public List<Category> getAll() {return categoryRepository.findAll();}

    @Transactional(readOnly = true)
    public Category getById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));
    }

    @Transactional
    public Category update(Long id, Category category) {

        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));

        existingCategory.setName(category.getName());
        existingCategory.setDescription(category.getDescription());
        return categoryRepository.save(existingCategory);
    }

    @Transactional
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Categoría no encontrada con ID: " + id);
        }
        categoryRepository.deleteById(id);
    }







}

