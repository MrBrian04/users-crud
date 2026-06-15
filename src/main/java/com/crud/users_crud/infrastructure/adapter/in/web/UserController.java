package com.crud.users_crud.infrastructure.adapter.in.web;


import com.crud.users_crud.domain.model.User;
import com.crud.users_crud.domain.port.in.UserUseCase;
import com.crud.users_crud.infrastructure.adapter.in.web.dto.UserRequest;
import com.crud.users_crud.infrastructure.adapter.in.web.dto.UserResponse;
import com.crud.users_crud.infrastructure.adapter.in.web.mapper.UserWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserUseCase userUseCase;
    private final UserWebMapper userWebMapper;

    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserRequest request) {
        User created = userUseCase.register(userWebMapper.toModel(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(userWebMapper.toResponse(created));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll() {
        List<UserResponse> responses = userUseCase.findAll()
                .stream()
                .map(userWebMapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userWebMapper.toResponse(userUseCase.findById(id)));
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<java.util.Map<String, Boolean>> existsById(@PathVariable Long id) {
        return ResponseEntity.ok(java.util.Map.of("exists", userUseCase.existsById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @Valid @RequestBody UserRequest request) {
        userUseCase.update(id, userWebMapper.toModel(request));
        User updatedUser = userUseCase.findById(id);

        return ResponseEntity.ok(userWebMapper.toResponse(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}