package com.johnnywey.flipside.failable;

public enum Fail {
    CONNECT_TIMEOUT(504),
    READ_TIMEOUT(598),
    SERVER_MISSING(503),
    NOT_READY(503),
    NOT_FOUND(404),
    BAD_REQUEST(400),
    BAD_RESPONSE(400),
    INVALID_PARAMETERS(400),
    REQUEST_TIMEOUT(408),
    UNKNOWN(500),
    INTERNAL_ERROR(500),
    ACCESS_DENIED(403),
    SUCCESS(200); // place holder

    private final Integer httpResponseCode;

    Fail(Integer httpResponseCodeIn) {
        httpResponseCode = httpResponseCodeIn;
    }

    public Integer getHttpResponseCode() {
        return httpResponseCode;
    }
}
