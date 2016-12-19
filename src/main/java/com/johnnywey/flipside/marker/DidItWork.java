package com.johnnywey.flipside.marker;

import com.johnnywey.flipside.failable.Fail;

/**
 * Markers are similar to options but exist only to communicate the result of an operation that does
 * not return a value. For example, a call to a database that only writes a record.
 *
 * Traditionally, if the operation fails null is returned or an exception is thrown. A bunch of try / catch
 * blocks around every similar operation is rough.
 *
 * Returning a Marker will allow the failure to be communicated up the chain without throwing an exception
 * or returning null. If the operation is successful, the information won't matter.
 * If it failed, the Failure information is easy to retrieve.
 */
public interface DidItWork {
    Fail getReason();
    String getDetail();
    Boolean isSuccess();

    /**
     * If the operation worked, invoke the specified consumer, otherwise do nothing.
     *
     * @param consumer the consumer to invoke if this worked
     */
    void ifItWorked(MarkerConsumer consumer);

    /**
     * Defines the 'Groovy Truth' of this Marker.
     *
     * @return true if this Marker indicates success, false otherwise
     * @see <a href="http://www.groovy-lang.org/semantics.html#Groovy-Truth">Groovy Truth</a>
     */
    boolean asBoolean();
}
