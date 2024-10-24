package com.example.Final.Project.Forum.exception;

public class InvalidEmailVerificationCode extends RuntimeException {
    public InvalidEmailVerificationCode(String message) {
        super(message);
    }
}
