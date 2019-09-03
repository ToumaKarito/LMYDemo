package com.lmy.lmydemo.net;

public class Response {
    private int code;
    private String result;

    public Response(int code, String result) {
        this.code = code;
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public String getResult() {
        return result;
    }
}
