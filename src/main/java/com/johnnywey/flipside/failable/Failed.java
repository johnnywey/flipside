package com.johnnywey.flipside.failable;

/**
 * Something failed.
 */
public class Failed<T> implements Failable<T> {

    private final Fail reason;
    private final String detail;

    public Failed(final Fail reason, final String detail) {
        this.reason = reason;
        this.detail = detail;
    }

    @Override
    public T get() {
        throw new UnsupportedOperationException("Cannot resolve value on Failed");
    }

    @Override
    public Boolean isSuccess() {
        return false;
    }

    @Override
    public Fail getReason() {
        return reason;
    }

    @Override
    public String getDetail() {
        return detail;
    }

    @Override
    public String toString() {
        return reason+": "+detail;
    }
}
