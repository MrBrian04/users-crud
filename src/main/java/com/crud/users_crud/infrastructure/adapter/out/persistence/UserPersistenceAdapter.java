package com.crud.users_crud.infrastructure.adapter.out.persistence;

import com.crud.users_crud.domain.model.User;
import com.crud.users_crud.domain.port.out.UserRepository;
import com.crud.users_crud.infrastructure.adapter.out.persistence.mapper.UserPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserRepository {

    private final UserJpaRepository jpaRepository;
    private final UserPersistenceMapper persistenceMapper;


    @Override
    public User save(User user) {
        return persistenceMapper.toModel(jpaRepository.save(persistenceMapper.toEntity(user)));
    }

    @Override
    public List<User> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(persistenceMapper::toModel)
                .toList();
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaRepository.findById(id).map(persistenceMapper::toModel);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(persistenceMapper::toModel);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}
