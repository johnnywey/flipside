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
    public static <T> Box<T> Some(T value) {
        new Some(value)
    }

    public static <T> Box<T> None() {
        new None()
    }
}
