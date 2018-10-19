package com.br.gamifit.model.exception;

public class LessThanFortyMinutesTrainingException extends Exception {

    public LessThanFortyMinutesTrainingException(String msg){
        super(msg);
    }

    public LessThanFortyMinutesTrainingException(){
        super();
    }
}
