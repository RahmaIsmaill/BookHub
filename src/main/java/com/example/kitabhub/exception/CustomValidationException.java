package com.example.kitabhub.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class CustomValidationException extends RuntimeException {

    private final Map<String, String> errors = new HashMap<>();

    public CustomValidationException(Map<String, String> errors) {
        this.errors.putAll(errors);
    }
    public Map<String, String> getErrors() {
        return errors;
    }
}

