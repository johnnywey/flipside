package com.johnnywey.flipside.box;

import java.io.Serializable;

/**
 * A Full box.
 *
 * Calling .get() will return the value.
 */
public class Some<T> implements Box<T>, Serializable {
    private static final long serialVersionUID = 1L;

    private final T result;

    public Some(T resultIn) {
        result = resultIn;
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
    public Boolean isEmpty() {
        return false;
    }

    @Override
    public void ifPresent(final BoxConsumer<? super T> consumer) {
        consumer.onValuePresent(result);
    }

    @Override
    public boolean asBoolean() {
        return true;
    }
}
