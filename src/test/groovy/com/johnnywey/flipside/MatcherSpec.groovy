package com.johnnywey.flipside

import com.johnnywey.flipside.*
import com.johnnywey.flipside.failable.Fail
import com.johnnywey.flipside.failable.Failable
import com.johnnywey.flipside.failable.Failed
import com.johnnywey.flipside.failable.Success
import spock.lang.Specification

import static com.johnnywey.flipside.Matcher.match
import static com.johnnywey.flipside.Boxes.None
import static com.johnnywey.flipside.Boxes.Some
import static com.johnnywey.flipside.Markers.DidNotWork
import static com.johnnywey.flipside.Markers.Worked
import static org.junit.Assert.fail

class MatcherSpec extends Specification {

    def "test things match"() {
        when:
        def foundString = false

        match "test" on {
            matches String, { foundString = true }
            matches Integer, { foundString = false }
        }

        then:
        foundString

        when:
        foundString = false
        def string = "test"
        match string on {
            matches "test", { foundString = true }
            matches String, { foundString = false }
        }
        then:
        foundString

        when:
        def foundInt = false
        match 10 on {
            matches "test", { foundInt = false }
            matches 10, { foundInt = true }
        }

        then:
        foundInt
    }

    def "test failable match"() {
        when:
        def container = null
        def option = new Success<String>("test")
        match option on {
            success { container = it }
        }

        then:
        container == "test"

        when:
        def result = null
        option = new Failed<String>(Fail.NOT_FOUND, "")
        match option on {
            success { result = null }
            failure { result = it }
        }

        then:
        result != null
        result.reason == Fail.NOT_FOUND
    }

    def "test diditwork match"() {
        setup:
        def option_worked = Worked()
        def option_failed = DidNotWork(Fail.BAD_REQUEST, "")
        def result_worked = null
        def result_did_not_work = null

        when:
        match option_worked on {
            failure { result_worked = null }
            success { result_worked = "worked!" }
        }

        match option_failed on {
            failure { result_did_not_work = it }
            success { result_did_not_work = null }
        }

        then:
        result_did_not_work != null
        result_worked != null
        result_did_not_work.reason == Fail.BAD_REQUEST
    }

    def "test boxes"() {
        setup:
        def test = "test"
        def full = Some(test)
        def empty = None()

        when:
        def result = match full on {
            some { it }
            none { "fail" }
        }
        then:
        result == test

        when:
        result = match empty on {
            some { "fail" }
            none { test }
        }
        then:
        result == test
    }

    def "test responses"() {
        setup:
        def response_success = [getStatusCode: {-> Fail.SUCCESS.httpResponseCode }] as ClientResponse
        def response_success_201 = [getStatusCode: {-> 201 }] as ClientResponse
        def response_failure = [
                getStatusCode: {-> Fail.ACCESS_DENIED.httpResponseCode },
                getStatusText: {-> "Access Denied." }
        ] as ClientResponse
        def expected_response_success = null
        def expected_response_success_201 = null
        Failable expected_response_failure = null

        when:
        match response_success on {
            success { expected_response_success = it }
            failure { fail("should not get here") }
        }
        match response_success_201 on {
            success { expected_response_success_201 = it }
            failure { fail("should not get here") }
        }
        match response_failure on {
            success { fail("should not get here") }
            failure { expected_response_failure = it }
        }

        then:
        response_success == expected_response_success
        response_success_201 == expected_response_success_201
        !expected_response_failure.isSuccess()
        expected_response_failure.reason == Fail.ACCESS_DENIED
        expected_response_failure.detail == "Access Denied."
    }

    def "matcher returns a value"() {
        expect:
        def result =
            match "test" on {
                matches "test", { true }
                false
            }

        result == true
    }
}
