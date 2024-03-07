package com.example.demo.mapper;

import com.example.demo.model.repository.User;
import com.example.demo.model.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {


    @Mapping(target = "name")
    @Mapping(target = "gender")
    UserResponse map(User user);
}