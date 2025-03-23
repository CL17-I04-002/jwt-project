package com.jwt.auth.D_Infraestructure.security.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handlerGenericException(HttpServletRequest request, Exception e){
        ApiError apiError = new ApiError();
        apiError.setBackendMessage(e.getLocalizedMessage());
        apiError.setUrl(request.getRequestURL().toString());
        apiError.setMethod(request.getMethod());
        apiError.setMessage("Internal Server error, do again");
        apiError.setTimestamp(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handlerAccessException(HttpServletRequest request, AccessDeniedException exception){
        ApiError apiError = new ApiError();
        apiError.setBackendMessage(exception.getLocalizedMessage());
        apiError.setUrl(request.getRequestURL().toString());
        apiError.setMethod(request.getMethod());
        apiError.setMessage("Access denied. You don't have enough permissions to access this function. Please, contact administrator");
        apiError.setTimestamp(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handlerMethodArgumentNotValidException(HttpServletRequest request,
                                                                    MethodArgumentNotValidException exception){
        ApiError apiError = new ApiError();
        apiError.setBackendMessage(exception.getLocalizedMessage());
        apiError.setUrl(request.getRequestURL().toString());
        apiError.setMethod(request.getMethod());
        apiError.setTimestamp(LocalDateTime.now());
        apiError.setMessage("Error in the request sent");
        System.out.println(
                exception.getAllErrors().stream().map(each -> each.getDefaultMessage())
                        .collect(Collectors.toList()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

}
