package com.johnnywey.flipside

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
    }

    def "test box is empty"() {
        setup:
        def empty = Boxes.None()

        when:
        empty.get()

        then:
        thrown(UnsupportedOperationException)
        empty.isEmpty()
    }
}
