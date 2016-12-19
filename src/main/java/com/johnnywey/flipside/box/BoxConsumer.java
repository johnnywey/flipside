package com.johnnywey.flipside.box;

/**
 * Represents a function that should be called if a {@link Box} has {@link Some}.
 * Use with {@link Box#ifPresent}.
 */
public interface BoxConsumer<T> {

    void onValuePresent(T t);
}
