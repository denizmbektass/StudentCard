package com.bilgeadam.exceptions;

import lombok.Getter;

@Getter
public class OralExamException extends RuntimeException{
    private final ErrorType errorType;

    public OralExamException(ErrorType errorType){
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public OralExamException(ErrorType errorType, String message){
        super(message);
        this.errorType = errorType;
    }
}
