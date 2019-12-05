package com.manage.qinggong.base;

public class Response {
    private String message;
    private Object data;

    public Response() {
    }

    public Response(String message) {
        this.message = message;
    }

    public Response(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
