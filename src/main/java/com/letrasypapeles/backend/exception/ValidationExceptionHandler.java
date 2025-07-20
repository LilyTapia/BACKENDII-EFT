package com.letrasypapeles.backend.exception;

import com.letrasypapeles.backend.dto.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MessageResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        MessageResponse response = new MessageResponse();
        
        String errorMessage = "Datos de entrada inválidos";
        FieldError fieldError = ex.getBindingResult().getFieldError();
        if (fieldError != null && fieldError.getDefaultMessage() != null) {
            errorMessage = fieldError.getDefaultMessage();
        }
        
        response.setMessage(errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
