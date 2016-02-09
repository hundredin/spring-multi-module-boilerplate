package com.spring.boilerplate.api.response;

public class CommonResponse {
    private Status status;
    private String message;

    public CommonResponse(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    public CommonResponse() {
    }
}
