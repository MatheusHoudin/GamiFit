package com.br.gamifit.model;

import java.util.Observable;

public class ObserverResponse {
    private String method;
    private Object content;

    public ObserverResponse(String method,Object content){
        this.setContent(content);
        this.setMethod(method);
    }

    public Object getContent() {
        return content;
    }

    public String getMethod() {
        return method;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public void setMethod(String method) {
        this.method = method;
    }

}
