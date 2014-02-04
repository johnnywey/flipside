package com.johnnywey.flipside

import com.johnnywey.flipside.box.Box
import com.johnnywey.flipside.box.None
import com.johnnywey.flipside.box.Some

/**
 * Easy way to construct boxes. Allows code like:
 *
 * {@code Some("test")}
 * {@code None()}
 */
class Boxes {
    public static Box Some(value) {
        new Some(value)
    }

    public static Box None() {
        new None()
    }
}
