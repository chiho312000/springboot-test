package com.example.demo.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class ApiResponse<T> {
    private int code;
    private String message;
    private T data;

    public static <T> ApiResponse<T> createSuccessResponse(T data) {
        return new ApiResponse<>(0, "success", data);
    }

    public static <T> ApiResponse<T> createResponse(int code, String message, T data) {
        return new ApiResponse<>(code, message, data);
    }
}
