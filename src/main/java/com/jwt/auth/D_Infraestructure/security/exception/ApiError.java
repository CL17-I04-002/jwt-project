package com.jwt.auth.D_Infraestructure.security.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiError {
    private String backendMessage;
    private String message;
    private LocalDateTime timestamp;
    private String url;
    private String method;
}
