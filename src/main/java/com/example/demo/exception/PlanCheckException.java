package com.example.demo.exception;

public class PlanCheckException extends RuntimeException {
    public PlanCheckException(String message) {
        super(message);
    }
}