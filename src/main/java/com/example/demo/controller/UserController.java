package com.example.demo.controller;

import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.mongoRepository.UserRepository;
import com.example.demo.model.repository.User;
import com.example.demo.model.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("/users")
@RestController
public class UserController extends ApiController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable String userId) throws RuntimeException {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) throw new NotFoundException();
        return userMapper.map(user);
    }
}
