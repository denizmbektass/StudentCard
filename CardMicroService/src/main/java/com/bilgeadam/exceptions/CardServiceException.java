package com.bilgeadam.exceptions;

import lombok.Getter;

@Getter
public class CardServiceException extends RuntimeException{
    private final ErrorType errorType;

    public CardServiceException(ErrorType errorType){
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public CardServiceException(ErrorType errorType, String message){
        super(message);
        this.errorType = errorType;
    }



}
