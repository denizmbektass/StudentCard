package com.bilgeadam.exceptions;

import lombok.Getter;

@Getter
public class ProjectException extends RuntimeException{
    private final ErrorType errorType;

    public ProjectException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public ProjectException(String message, ErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }
}
