package com.spring.boilerplate.api.response;

public class FailResponse extends CommonResponse {
    public FailResponse(String message) {
        super(Status.FAIL, message);
    }
}
