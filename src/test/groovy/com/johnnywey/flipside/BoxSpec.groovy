package com.johnnywey.flipside

import com.johnnywey.flipside.box.Box
import spock.lang.Specification

class BoxSpec extends Specification {

    def "test box is full"() {
        setup:
        def string = "test"

        when:
        def full = Boxes.Some(string)

        then:
        !full.isEmpty()
        full.get() == string
        full.getOrElse("OTHER") == string
    }

    def "test box is empty"() {
        setup:
        def empty = Boxes.None()

        when:
        empty.get()

        then:
        thrown(UnsupportedOperationException)
        empty.isEmpty()
        empty.getOrElse("ELSE") == "ELSE"
    }

    def "test parameterized static factory methods"() {
        expect:
        aJavaStyleMethod(content).isEmpty() == empty

        where:
        content | empty
        ""      | true
        "test"  | false
    }

    def "test groovy truth"() {
        expect:
        Boxes.Some("some")
        !Boxes.None()
    }

    def "test if present callback"() {
        setup:
        def callbackWasCalled = false

        when: "the box is empty"
        Boxes.None().ifPresent { callbackWasCalled = true }

        then: "the callback should not be called"
        !callbackWasCalled

        when: "the box is full"
        Boxes.Some("Presents!").ifPresent { contents ->
            assert contents == "Presents!"
            callbackWasCalled = true
        }

        then: "the callback should have been called"
        callbackWasCalled
    }

    private static Box<String> aJavaStyleMethod(final String contents) {
        if (contents) {
            return Boxes.Some(contents)
        }

        return Boxes.None()
    }
}
