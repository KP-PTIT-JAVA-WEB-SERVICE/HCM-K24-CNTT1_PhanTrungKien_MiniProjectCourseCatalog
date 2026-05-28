package com.ptit.course_catalog.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private boolean success;
    private int code;
    private String message;
    private LocalDateTime timestamp;
    private T data;

    public static <T> ApiResponse<T> success(int code, String message, T data) {
        return new ApiResponse<>(true, code, message, LocalDateTime.now(), data);
    }

    public static <T> ApiResponse<T> error(int code, String message, T data) {
        return new ApiResponse<>(false, code, message, LocalDateTime.now(), data);
    }

    public static <T> ApiResponse<T> error(int code, String message) {
        return new ApiResponse<>(false, code, message, LocalDateTime.now(), null);
    }
}
