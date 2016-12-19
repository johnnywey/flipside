package com.johnnywey.flipside.box;

/**
 * Wrapped interface for a Box. Meant to be similar to a Scala Option
 * (see <a href="http://en.wikipedia.org/wiki/Option_type">http://en.wikipedia.org/wiki/Option_type</a>}).
 * 
 * A Box is either full or empty. If full, you can call .get() and retrieve the value. If empty,
 * calling .get() will throw an exception.
 */
public interface Box<T> {
    T get();
    T getOrElse(T alternateIn);
    Boolean isEmpty();

    /**
     * If the box is full, invoke the specified consumer with the value of {@link #get()}, otherwise do nothing.
     * Works similarly to {@code Optional#ifPresent} in JDK 8+.
     *
     * @param consumer the consumer to invoke if this Box is full
     */
    void ifPresent(BoxConsumer<? super T> consumer);

    /**
     * Defines the 'Groovy Truth' of this Box.
     *
     * @return true if this Box has contents, false otherwise
     * @see <a href="http://www.groovy-lang.org/semantics.html#Groovy-Truth">Groovy Truth</a>
     */
    boolean asBoolean();
}
