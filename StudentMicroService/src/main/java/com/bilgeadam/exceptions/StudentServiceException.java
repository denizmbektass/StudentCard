package com.bilgeadam.exceptions;

import lombok.Getter;

@Getter
public class StudentServiceException extends RuntimeException {

    private final ErrorType errorType;

    public StudentServiceException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public StudentServiceException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }


}
