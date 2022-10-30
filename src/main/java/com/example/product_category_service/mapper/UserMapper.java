package com.example.product_category_service.mapper;

import com.example.product_category_service.dto.CreateUserDto;
import com.example.product_category_service.dto.UserDto;
import com.example.product_category_service.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User map(CreateUserDto userDto);
    UserDto map(User user);



}
