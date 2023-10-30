package com.bilgeadam.exceptions;

import lombok.Getter;

@Getter
public class TrainerAssessmentException extends RuntimeException{

    private final ErrorType errorType;

    public TrainerAssessmentException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }
}