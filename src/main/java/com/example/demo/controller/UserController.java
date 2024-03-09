package com.example.demo.controller;

import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.mongoRepository.UserRepository;
import com.example.demo.model.repository.User;
import com.example.demo.model.response.UserResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.NotAcceptableStatusException;

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

    @PostMapping("")
    public String createUser(@RequestBody @Valid UserResponse user, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new NotAcceptableStatusException(bindingResult.getFieldError().getDefaultMessage());
        User result = userRepository.insert(userMapper.map(user));
        if (result == null) throw new NotAcceptableStatusException("");
        return result.get_id().toHexString();
    }

}
