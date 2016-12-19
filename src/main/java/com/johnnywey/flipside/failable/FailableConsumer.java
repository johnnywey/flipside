package com.johnnywey.flipside.failable;

/**
 * Represents a function that should be called upon successful completion of a {@link Failable}.
 * Use with {@link Failable#ifSuccessful}.
 */
public interface FailableConsumer<T> {

    void onSuccess(T t);
}
