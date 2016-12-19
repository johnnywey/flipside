package com.johnnywey.flipside.failable;

import com.johnnywey.flipside.marker.DidItWork;

/**
 * This is a simple wrapper for communicating failure data. It is loosely based on a Scala Option but provides
 * a common interface for failure cases that can be easily mapped to things like HTTP codes, etc.
 */
public interface Failable<T> {
    T get();
    T getOrElse(T alternateIn);
    Boolean isSuccess();
    Fail getReason();
    String getDetail();
    DidItWork toDidItWork();

    /**
     * If the operation is successful, invoke the specified consumer with the value of {@link #get()},
     * otherwise do nothing.  Works similarly to {@code Optional#ifPresent} in JDK 8+.
     *
     * @param consumer the consumer to invoke if this Failable succeeded
     */
    void ifSuccessful(FailableConsumer<? super T> consumer);

    /**
     * Defines the 'Groovy Truth' of this Failable.
     *
     * @return true if this Failable indicates success, false otherwise
     * @see <a href="http://www.groovy-lang.org/semantics.html#Groovy-Truth">Groovy Truth</a>
     */
    boolean asBoolean();
}
