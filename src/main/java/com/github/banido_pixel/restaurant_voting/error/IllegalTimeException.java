package com.github.banido_pixel.restaurant_voting.error;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.http.HttpStatus;

import static org.springframework.boot.web.error.ErrorAttributeOptions.Include.MESSAGE;

public class IllegalTimeException extends AppException{
    public IllegalTimeException(String message) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, message, ErrorAttributeOptions.of(MESSAGE));
    }
}
