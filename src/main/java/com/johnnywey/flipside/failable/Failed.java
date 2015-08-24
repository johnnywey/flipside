package com.johnnywey.flipside.failable;

import com.johnnywey.flipside.marker.DidNotWork;
import com.johnnywey.flipside.marker.DidItWork;

import java.io.Serializable;

/**
 * Something failed.
 */
public class Failed<T> implements Failable<T>, Serializable {
    private static final long serialVersionUID = 1L;

    private final Fail reason;
    private final String detail;

    public Failed(final Fail reason, final String detail) {
        this.reason = reason;
        this.detail = detail;
    }

    @Override
    public T get() {
        throw new FailableException(this);
    }

    @Override
    public T getOrElse(T alternateIn) {
        return alternateIn;
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
    public DidItWork toDidItWork() {
        return new DidNotWork(this.reason, this.detail);
    }

    @Override
    public String toString() {
        return getReason() + " - " + getDetail();
    }
}
