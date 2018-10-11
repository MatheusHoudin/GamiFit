package com.br.gamifit.database;

public class OperationResult {
    private Exception caughtException;
    private int operationCode;

    public OperationResult(Exception caughtException,int operationCode){
        this.setCaughtException(caughtException);
        this.setOperationCode(operationCode);
    }

    public Exception getCaughtException() {
        return caughtException;
    }

    public void setCaughtException(Exception caughtException) {
        this.caughtException = caughtException;
    }

    public int getOperationCode() {
        return operationCode;
    }

    public void setOperationCode(int operationCode) {
        this.operationCode = operationCode;
    }
}
