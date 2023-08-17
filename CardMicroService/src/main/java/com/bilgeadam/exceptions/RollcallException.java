package com.bilgeadam.exceptions;

import lombok.Getter;

@Getter
public class RollcallException extends RuntimeException{
    private final ErrorType errorType;

    public RollcallException(ErrorType errorType){
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public RollcallException(ErrorType errorType, String message){
        super(message);
        this.errorType = errorType;
    }
}
