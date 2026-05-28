package com.crud.users_crud.infrastructure.adapter.in.web.mapper;

import com.crud.users_crud.domain.model.User;
import com.crud.users_crud.infrastructure.adapter.in.web.dto.UserRequest;
import com.crud.users_crud.infrastructure.adapter.in.web.dto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserWebMapper {

    @Mapping(target = "id", ignore = true)
    User toModel(UserRequest request);

    UserResponse toResponse(User user);
}
