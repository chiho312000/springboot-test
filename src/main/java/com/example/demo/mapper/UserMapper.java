package com.example.demo.mapper;

import com.example.demo.model.repository.User;
import com.example.demo.model.response.ApiResponse;
import com.example.demo.model.response.UserResponse;
import com.example.demo.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;

@Mapper(uses = {ConditionMapper.class})
@Component
public interface UserMapper {

    @Mapping(target = "passwordHash", source = "password", qualifiedByName = "setPasswordHash")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "_id", ignore = true)
    User map(UserResponse userResponse);

    @Mapping(target = "password", ignore = true)
    UserResponse map(User user);



    @Named("setPasswordHash")
    default String setPasswordHash(String password) {
        return CommonUtil.hashPassword(password);
    }
}
