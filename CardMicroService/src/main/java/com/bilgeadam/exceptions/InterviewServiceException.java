package com.bilgeadam.exceptions;

import lombok.Getter;

@Getter
public class InterviewServiceException extends RuntimeException {
    private final ErrorType errorType;

    public InterviewServiceException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public InterviewServiceException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;

    }
}
