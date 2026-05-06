package com.crud.users_crud.controller;

import com.crud.users_crud.entity.Supplier;
import com.crud.users_crud.service.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {
    private final SupplierService supplierService;

    @PostMapping
    public ResponseEntity<Supplier> create(@Valid @RequestBody Supplier supplier) {
        Supplier created = supplierService.create(supplier);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<Supplier>> getAll() {
        List<Supplier> suppliers = supplierService.getAll();
        return ResponseEntity.ok(suppliers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Supplier> getById(@PathVariable Long id) {
        Supplier supplier = supplierService.getById(id);
        return ResponseEntity.ok(supplier);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Supplier> update(@PathVariable Long id, @Valid @RequestBody Supplier supplier) {
        Supplier updated = supplierService.update(id, supplier);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        supplierService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
