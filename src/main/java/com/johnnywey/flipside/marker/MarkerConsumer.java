package com.johnnywey.flipside.marker;

/**
 * Represents a function that should be called once a {@link DidItWork} has {@link Worked}.
 * Use with {@link DidItWork#ifItWorked}.
 */
public abstract class MarkerConsumer {

    abstract void onItWorked();
}
