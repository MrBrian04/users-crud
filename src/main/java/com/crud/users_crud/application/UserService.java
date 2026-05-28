package com.crud.users_crud.application;

import com.crud.users_crud.domain.exception.DomainException;
import com.crud.users_crud.domain.exception.UserNotFoundException;
import com.crud.users_crud.domain.model.User;
import com.crud.users_crud.domain.port.in.UserUseCase;
import com.crud.users_crud.domain.port.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserUseCase {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public User register(User user) {
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new DomainException("Un usuario ya existe con el correo electronico: " + user.getEmail());
        }
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional
    @Override
    public void update(Long id, User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        existingUser.updateWith(user);
        userRepository.save(existingUser);

    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);

    }
}
