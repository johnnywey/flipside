package com.johnnywey.flipside

import com.johnnywey.flipside.failable.Fail
import com.johnnywey.flipside.failable.Failable
import com.johnnywey.flipside.failable.Failed
import com.johnnywey.flipside.failable.Success

/**
 * Easy way to construct {@link Failable}. Allows code like:
 *
 * {@code Success("test")}
 * {@code Failed(Fail.INVALID , "Invalid")}
 */
class Failables {
    public static Failable Failed(Fail reason, String detail) {
        new Failed(reason, detail)
    }

    public static Failable Succeeded(value) {
        new Success(value)
    }
}
