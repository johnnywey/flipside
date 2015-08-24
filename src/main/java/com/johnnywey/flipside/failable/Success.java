package com.johnnywey.flipside.failable;

import com.johnnywey.flipside.marker.Worked;
import com.johnnywey.flipside.marker.DidItWork;

import java.io.Serializable;

/**
 * Something succeeded.
 */
public class Success<T> implements Failable<T>, Serializable {
    private static final long serialVersionUID = 1L;

    private final T result;

    public Success(final T result) {
        this.result = result;
    }

    @Override
    public T get() {
        return result;
    }

    @Override
    public T getOrElse(T alternateIn) {
        return this.get();
    }

    @Override
    public Boolean isSuccess() {
        return true;
    }

    @Override
    public Fail getReason() {
        return Fail.SUCCESS;
    }

    @Override
    public String getDetail() {
        return Fail.SUCCESS.name();
    }

    @Override
    public DidItWork toDidItWork() {
        return new Worked();
    }

    @Override
    public String toString() {
        return getReason() + " - " + get();
    }
}
