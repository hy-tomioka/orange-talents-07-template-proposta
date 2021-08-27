package br.com.zupacademy.yudi.proposta.shared.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<StandardError> handleResponseEntityException(ResponseStatusException exception) {
        return ResponseEntity.status(exception.getStatus())
                .body(new StandardError(exception.getReason()));
    }
}
