package com.crud.users_crud.service;

import com.crud.users_crud.entity.Supplier;
import com.crud.users_crud.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;

    @Transactional
    public Supplier create(Supplier supplier) {
        if (supplierRepository.findByName(supplier.getName()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un proveedor con el nombre: " + supplier.getName());
        }
        return supplierRepository.save(supplier);
    }

    @Transactional(readOnly = true)
    public List<Supplier> getAll() {
        return supplierRepository.findAll();
    }



    @Transactional(readOnly = true)
    public Supplier getById(Long id) {
        return supplierRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    @Transactional
    public Supplier update(Long id, Supplier supplier) {
        Supplier existingSupplier = supplierRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + id));

        existingSupplier.setName(supplier.getName());
        existingSupplier.setEmail(supplier.getEmail());
        existingSupplier.setPhone(supplier.getPhone());
        existingSupplier.setIsActive(supplier.getIsActive());
        return supplierRepository.save(existingSupplier);
    }

    @Transactional
    public void delete(Long id) {
        if (!supplierRepository.existsById(id)) {
            throw new RuntimeException("Proveedor no encontrado con ID: " + id);
        }
        supplierRepository.deleteById(id);
    }

}
