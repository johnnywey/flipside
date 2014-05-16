package com.johnnywey.flipside.failable;

/**
 * Wrapper exception for objects that fail.
 */
public class FailableException extends RuntimeException {
    private Failable failure;

    public FailableException(Failable failure) {
        this.failure = failure;
    }

    @Override
    public String getMessage() {
        return failure.getDetail();
    }

    public Integer getHttpStatusCode() {
        return failure.getReason().getHttpResponseCode();
    }

    public String getGenericError() {
        return failure.getReason().name();
    }
}