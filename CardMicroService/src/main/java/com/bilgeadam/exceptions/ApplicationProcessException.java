package com.bilgeadam.exceptions;

import lombok.Getter;

@Getter
public class ApplicationProcessException extends RuntimeException {
    private final ErrorType errorType;

    public ApplicationProcessException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public ApplicationProcessException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }
}
