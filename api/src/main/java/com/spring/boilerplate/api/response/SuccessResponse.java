package com.spring.boilerplate.api.response;

public class SuccessResponse extends CommonResponse {
    public SuccessResponse(String message) {
        super(Status.SUCCESS, message);
    }
}
