package com.bilgeadam.exceptions;

import lombok.Getter;

@Getter
public class WeightsException extends RuntimeException{
    private final ErrorType errorType;

    public WeightsException(ErrorType errorType){
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public WeightsException(ErrorType errorType, String message){
        super(message);
        this.errorType = errorType;
    }
}
