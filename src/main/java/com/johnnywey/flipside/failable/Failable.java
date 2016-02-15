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
     * Defines the 'Groovy Truth' of this Failable.
     *
     * @return true if this Failable indicates success, false otherwise
     * @see <a href="http://www.groovy-lang.org/semantics.html#Groovy-Truth">Groovy Truth</a>
     */
    boolean asBoolean();
}
