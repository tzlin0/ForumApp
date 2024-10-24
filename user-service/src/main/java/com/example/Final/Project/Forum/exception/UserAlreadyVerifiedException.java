package com.example.Final.Project.Forum.exception;

public class UserAlreadyVerifiedException extends RuntimeException {
    public UserAlreadyVerifiedException(String message) {
        super("User is already verified");
    }
}
