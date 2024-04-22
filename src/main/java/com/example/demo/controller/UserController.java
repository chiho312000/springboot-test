package com.example.demo.controller;

import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.mongoRepository.UserRepository;
import com.example.demo.model.repository.User;
import com.example.demo.model.request.LoginRequest;
import com.example.demo.model.response.ApiResponse;
import com.example.demo.model.response.UserResponse;
import com.example.demo.util.CommonUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.NotAcceptableStatusException;
import org.springframework.http.MediaType;

@RequestMapping("/users")
@RestController
@Slf4j
public class UserController extends ApiController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<UserResponse> getUser(@PathVariable String userId) throws RuntimeException {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) throw new NotFoundException();
        return ApiResponse.createSuccessResponse(userMapper.map(user));
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<String> createUser(@RequestBody @Valid UserResponse user, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new NotAcceptableStatusException(bindingResult.getFieldError().getDefaultMessage());
        User result = userRepository.insert(userMapper.map(user));
        if (result == null) throw new NotAcceptableStatusException("");
        return ApiResponse.createSuccessResponse(result.get_id().toHexString());
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<?> login(@RequestBody @Valid LoginRequest user, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new NotAcceptableStatusException(bindingResult.getFieldError().getDefaultMessage());
        User result = userRepository.getLoginUser(user.getEmail(), CommonUtil.hashPassword(user.getPassword()));
        if (result == null) {
            log.info("user not found");
            throw new NotAcceptableStatusException("Email or password incorrect");
        }
        return ApiResponse.createSuccessResponse(null);
    }

}
