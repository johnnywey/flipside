package com.johnnywey.flipside.box;

import java.io.Serializable;

/**
 * An empty Box.
 * 
 * Calling .get() will throw an exception.
 */
public class None<T> implements Box<T>, Serializable {
	private static final long serialVersionUID = 1L;

    @Override
    public T get() {
        throw new UnsupportedOperationException("Cannot resolve value on None");
    }

    @Override
    public T getOrElse(T alternateIn) {
        return alternateIn;
    }

    @Override
    public Boolean isEmpty() {
        return true;
    }

    @Override
    public void ifPresent(final BoxConsumer<T> consumer) {
        // Do nothing
    }

    @Override
    public boolean asBoolean() {
        return false;
    }
}
