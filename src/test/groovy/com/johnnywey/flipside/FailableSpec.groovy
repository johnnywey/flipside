package com.johnnywey.flipside

import com.johnnywey.flipside.failable.Fail
import com.johnnywey.flipside.failable.FailableException
import spock.lang.Specification

class FailableSpec extends Specification {

    def "test failed"() {
        setup:
        def errMsg = "This is a test failed"
        def unit = Failables.Failed(Fail.BAD_REQUEST, errMsg)

        expect:
        !unit.isSuccess()
        unit.detail.matches(errMsg)
        unit.reason.equals(Fail.BAD_REQUEST)
        unit.toString().equals(Failables.Failed(Fail.BAD_REQUEST, errMsg).toString())
        unit.reason.httpResponseCode.equals(Fail.BAD_REQUEST.httpResponseCode)

        unit.toDidItWork().detail.matches(errMsg)
        unit.toDidItWork().reason.equals(Fail.BAD_REQUEST)
    }

    def "test exception"() {
        setup:
        def errMsg = "This is a test failed"
        def unit = Failables.Failed(Fail.BAD_REQUEST, errMsg)

        when:
        unit.get()

        then:
        thrown(FailableException)
    }

    def "test success"() {
        setup:
        def successMsg = "mySuccess"
        def unit = Failables.Succeeded(successMsg)

        expect:
        unit.isSuccess()
        unit.reason.equals(Fail.SUCCESS)
        unit.detail.equals(Fail.SUCCESS.name())
        unit.get().equals(successMsg)
        unit.toDidItWork().isSuccess()
    }

    def "test enum"() {
        expect:
        Fail.fromHttpResponseCode(202) == Fail.SUCCESS
        Fail.fromHttpResponseCode(404) == Fail.NOT_FOUND
        Fail.fromHttpResponseCode(403) == Fail.ACCESS_DENIED
        Fail.fromHttpResponseCode(504) == Fail.CONNECT_TIMEOUT
        Fail.fromHttpResponseCode(10020) == null
        Fail.fromHttpResponseCode(null) == null
    }
}
