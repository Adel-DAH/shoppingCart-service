package com.shoppyng.cart.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ShoppingCartErrorHandler {


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity handleIllegalArgumentException(IllegalArgumentException iae) {
        return ResponseEntity.badRequest().body(iae.getMessage());
    }


    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity handleIllegalArgumentException(NoSuchElementException iae) {
        return ResponseEntity.status(404).body(iae.getMessage());
    }

}
