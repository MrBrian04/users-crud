package com.crud.users_crud.infrastructure.adapter.out.persistence.mapper;

import com.crud.users_crud.domain.model.User;
import com.crud.users_crud.infrastructure.adapter.out.persistence.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserPersistenceMapper {

    UserEntity toEntity(User user);

    User toModel(UserEntity entity);

}