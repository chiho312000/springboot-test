package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR,reason = "Data Error")
public class InternalServerErrorException extends RuntimeException {
    private static final long serialVersionUID = 1810603568641688462L;

    public InternalServerErrorException(String message) {
        super("Data Error - "+message);
    }

}
