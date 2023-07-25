package com.bilgeadam.exceptions;

import lombok.Getter;

@Getter
public class AbsenceException extends RuntimeException {
    private final ErrorType errorType;

    public AbsenceException(ErrorType errorType){
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public AbsenceException(ErrorType errorType, String message){
        super(message);
        this.errorType = errorType;
    }
}
