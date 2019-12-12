package com.manage.qinggong.base.pojo;

public class Response {
    private int code;
    private String message;
    private Object data;

    public Response() {
    }

    public Response(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public Response(String message, int code, Object data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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
