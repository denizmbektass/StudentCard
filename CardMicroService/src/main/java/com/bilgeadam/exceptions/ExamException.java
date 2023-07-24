package com.bilgeadam.exceptions;

import lombok.Getter;

@Getter
public class ExamException extends RuntimeException{
    private final ErrorType errorType;

    public ExamException(ErrorType errorType){
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public ExamException(ErrorType errorType, String message){
        super(message);
        this.errorType = errorType;
    }
}
