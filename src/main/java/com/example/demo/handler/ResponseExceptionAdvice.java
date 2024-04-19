package com.example.demo.handler;

import com.fasterxml.jackson.databind.util.ExceptionUtil;
import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoWriteException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@ControllerAdvice
public class ResponseExceptionAdvice extends ResponseEntityExceptionHandler {

    private static final HttpHeaders httpHeaders = new HttpHeaders();

    @Value("${packageName}")
    private String packageName;

    static {
        httpHeaders.add("Access-Control-Allow-Origin", "*");
        httpHeaders.add("Cache-Control", "must-revalidate,no-cache,no-store,no-transform,proxy-revalidate");
        httpHeaders.add("Expires", "0");
        httpHeaders.add("Pragma", "no-cache");
    }

    @Autowired
    protected HttpServletRequest httpRequest;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleResponseException(Exception oldEx, WebRequest request) {
        Throwable ex = oldEx.getCause();
        if (ex == null) ex = oldEx;
        ResponseStatus status = ex.getClass().getAnnotation(ResponseStatus.class);
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        boolean needLogStatck = false;
        Object responseBody = null;
        if (ex instanceof ValidationException vex) {
            httpStatus = HttpStatus.BAD_REQUEST;
            log.info("Validation Message : {}", ex.getMessage());
        } else if (ex instanceof MongoWriteException dex) {
            if (dex.getCode() == 11000) {
                httpStatus = HttpStatus.NOT_ACCEPTABLE;
                log.info("Duplicate key");
            } else {
                needLogStatck = true;
            }
        } else {
            needLogStatck = true;

        }

        if (needLogStatck) {
            String[] stackTrace = ExceptionUtils.getRootCauseStackTrace(ex);
            if (stackTrace != null && stackTrace.length > 0) {
                int count = 0;
                for (String trace: stackTrace) {
                    if (trace.contains(packageName)) {
                        log.error("{} : {}", count == 0 ? "httpstatus msg" : "end point", trace);
                        count++;
                    }

                }
            }
        }


        if (status != null) {
            httpStatus = status.code();
        }

        return new ResponseEntity<>(responseBody, httpHeaders, httpStatus);
    }
}
