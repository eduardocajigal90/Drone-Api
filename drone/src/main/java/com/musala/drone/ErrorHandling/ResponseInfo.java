package com.musala.drone.ErrorHandling;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ResponseInfo<T> {

    @JsonProperty("result")
    private T result;

    @JsonProperty("status_code")
    private int statusCode;

    @JsonProperty("uri")
    private String uriRequested;

    @JsonProperty("message")
    private String message;

    public ResponseInfo(int statusCode, T result, String uriRequested, String message) {
        this.message=message;
        this.result = result;
        this.statusCode = statusCode;
        this.uriRequested = uriRequested;
    }
}
