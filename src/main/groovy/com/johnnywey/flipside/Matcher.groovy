package com.johnnywey.flipside

import com.johnnywey.flipside.ClientResponse
import com.johnnywey.flipside.failable.Fail
import com.johnnywey.flipside.failable.Failable
import com.johnnywey.flipside.failable.Failed
import com.johnnywey.flipside.box.Box
import com.johnnywey.flipside.marker.DidItWork


class Matcher<T> {
    private final static SUCCESS_PATTERN = /2\d{2}/ // only supports HTTP 2xx for now ...
    private final Closure closure
    private final T value
    private Boolean awaitingMatch = true
    private Object returnValue = null

    private Matcher(Closure closureIn, T valueIn) {
        closure = closureIn
        value = valueIn
    }

    /**
     * Match an incoming object on a type of match closure.
     *
     * For example:
     *
     * {@code match "test" on {matches "test", {println "Test matches!"}}}
     *
     * @param incoming The incoming object to match
     */
    public static def match(Object incoming) {
        [on: { Closure closure ->
            def delegate = new Matcher(closure, incoming)
            closure.delegate = delegate
            closure.call(incoming)
            delegate.returnValue
        }]
    }

    /**
     * Class Matcher.
     *
     * Allows matches like:
     * {@code match "test" on {matches String, {println "Test matches!"}}}
     *
     * @param klazz The class to match against
     * @param closure The closure to execute if the match is successful
     */
    public def matches(Class klazz, Closure closure) {
        if (awaitingMatch && value.class == klazz && awaitingMatch) {
            call(closure)
        }
    }

    /**
     * Object value Matcher.
     *
     * Allows matches like:
     * {@code match 10 on {matches 10, {println "It matches!"}}}
     *
     * @param klazz The object to match against
     * @param closure The closure to execute if the match is successful
     */
    public def matches(Object val, Closure closure) {
        if (awaitingMatch && val == value) {
            call(closure)
        }
    }

    /**
     * Success closure. Will be executed when:
     * <ul>
     *     <li>The result is a {@link Failable} and is successful</li>
     *     <li>The result is a {@link DidItWork} and is successful</li>
     *     <li>The result is an implementation of {@link ClientResponse} and the status code was an HTTP success</li>
     * </ul>
     *
     * @param closure The closure to execute if successful
     */
    public def success(Closure closure) {
        if (awaitingMatch && value instanceof Failable && (value as Failable).isSuccess()) {
            call(closure, value.get())
        } else if (awaitingMatch && value instanceof DidItWork && (value as DidItWork).isSuccess()) {
            call(closure)
        } else if (awaitingMatch && value instanceof ClientResponse && (value as ClientResponse).statusCode ==~ SUCCESS_PATTERN) {
            call(closure, value)
        }
    }

    /**
     * Failure closure. Will be executed when:
     * <ul>
     *     <li>The result is a {@link Failable} and is not successful</li>
     *     <li>The result is a {@link DidItWork} and is not successful</li>
     *     <li>The result is an implementation of {@link ClientResponse} and the status code was not an HTTP success</li>
     * </ul>
     *
     * @param closure The closure to execute if successful
     */
    public def failure(Closure closure) {
        if (awaitingMatch && value instanceof Failable && !(value as Failable).isSuccess()) {
            call(closure)
        } else if (awaitingMatch && value instanceof DidItWork && !(value as DidItWork).isSuccess()) {
            call(closure)
        } else if (awaitingMatch && value instanceof ClientResponse && !((value as ClientResponse).statusCode ==~ SUCCESS_PATTERN)) {
            // create a Fail out of the value
            Fail fail = Fail.values().find { it.httpResponseCode == (value as ClientResponse).statusCode } ?: Fail.UNKNOWN
            call(closure, new Failed(fail, value.statusText))
        }
    }

    /**
     * Box closure. Will be executed when an incoming {@link Box} is full and the value will be passed in.
     *
     * @param closure The closure to execute if full
     */
    public def some(Closure closure) {
        if (awaitingMatch && value instanceof Box && !(value as Box).isEmpty()) {
            call(closure, value.get())
        }
    }

    /**
     * Box closure. Will be executed when an incoming {@link Box} is empty.
     *
     * @param closure The closure to execute if empty
     */
    public def none(Closure closure) {
        if (awaitingMatch && value instanceof Box && (value as Box).isEmpty()) {
            call(closure)
        }
    }

    private def call(Closure closure) {
        awaitingMatch = false
        returnValue = closure.call(value)
    }

    private def call(Closure closure, incoming) {
        awaitingMatch = false
        returnValue = closure.call(incoming)
    }
}
