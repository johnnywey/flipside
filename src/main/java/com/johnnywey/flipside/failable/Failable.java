package com.johnnywey.flipside.failable;

/**
 * This is a simple wrapper for communicating failure data. It is loosely based on a Scala Option but provides
 * a common interface for failure cases that can be easily mapped to things like HTTP codes, etc.
 */
public interface Failable<T> {
    T get();
    Boolean isSuccess();
    Fail getReason();
    String getDetail();
}
