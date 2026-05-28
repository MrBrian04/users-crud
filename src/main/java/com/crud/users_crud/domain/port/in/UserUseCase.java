package com.crud.users_crud.domain.port.in;

import com.crud.users_crud.domain.model.User;

import java.util.List;

public interface UserUseCase {

    User register(User user);

    List<User> findAll();

    User findById(Long id);

    void update(Long id, User user);

    void delete(Long id);
}
