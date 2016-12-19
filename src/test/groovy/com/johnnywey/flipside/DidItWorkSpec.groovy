package com.johnnywey.flipside

import com.johnnywey.flipside.failable.Fail
import spock.lang.Specification

class DidItWorkSpec extends Specification {
    def "test worked"() {
        expect:
        Markers.Worked().isSuccess()
        Markers.Worked().reason == Fail.SUCCESS
        Markers.Worked().detail == Fail.SUCCESS.name()
    }

    def "test did not work"() {
        setup:
        def fail = Fail.ACCESS_DENIED
        def reason = "REASON"
        def unit = Markers.DidNotWork(fail, reason)

        expect:
        unit.reason == fail
        unit.detail == reason
        !unit.isSuccess()
    }

    def "test groovy truth"() {
        expect:
        Markers.Worked()
        !Markers.DidNotWork(Fail.BAD_REQUEST, "failure")
    }

    def "test it worked callback"() {
        setup:
        def callbackWasCalled = false

        when: "it didn't work :("
        Markers.DidNotWork(Fail.INTERNAL_ERROR, "This failed").ifItWorked { callbackWasCalled = true }

        then: "the callback should not be called"
        !callbackWasCalled

        when: "it worked!"
        Markers.Worked().ifItWorked() { callbackWasCalled = true }

        then: "the callback should have been called"
        callbackWasCalled
    }
}
