package com.example.demo.controller;


import com.example.demo.model.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {


    @GetMapping("/index")
    public ApiResponse<String> index() {
        return ApiResponse.createSuccessResponse("It works");
    }
}
