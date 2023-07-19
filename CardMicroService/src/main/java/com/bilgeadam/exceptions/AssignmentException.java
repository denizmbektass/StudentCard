package com.bilgeadam.exceptions;

import lombok.Getter;

@Getter
public class AssignmentException extends RuntimeException{
    private final ErrorType errorType;

    public AssignmentException(ErrorType errorType){
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public AssignmentException(ErrorType errorType, String message){
        super(message);
        this.errorType = errorType;
    }
}
