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
}
