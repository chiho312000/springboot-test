package com.example.demo.mapper;

import com.example.demo.model.repository.User;
import com.example.demo.model.response.UserResponse;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(uses = {ConditionMapper.class})
@Component
public interface UserMapper {

    UserResponse map(User user);

    User map(UserResponse userResponse);
}
