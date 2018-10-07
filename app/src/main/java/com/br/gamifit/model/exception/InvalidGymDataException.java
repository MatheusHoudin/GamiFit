package com.br.gamifit.model.exception;

public class InvalidGymDataException extends IllegalArgumentException {
    public Object obj;
    public InvalidGymDataException(String msg,Object obj){
        super(msg);
        this.obj = obj;
    }
}
