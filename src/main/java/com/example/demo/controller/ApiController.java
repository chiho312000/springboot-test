package com.example.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/api/")
public class ApiController {

    @GetMapping("index")
    public String index() {
        return "It works";
    }
}
