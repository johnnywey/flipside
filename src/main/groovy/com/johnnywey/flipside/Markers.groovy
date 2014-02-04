package com.johnnywey.flipside

import com.johnnywey.flipside.failable.Fail
import com.johnnywey.flipside.marker.DidItWork
import com.johnnywey.flipside.marker.DidNotWork
import com.johnnywey.flipside.marker.Worked

/**
 * Easy way to construct {@link DidItWork}. Allows code like:
 *
 * {@code Worked()}
 * {@code DidNotWork(Fail.INVALID, "Invalid")}
 */
class Markers {
    public static DidItWork Worked() {
        new Worked()
    }

    public static DidItWork DidNotWork(Fail failure, String message) {
        new DidNotWork(failure, message)
    }
}
