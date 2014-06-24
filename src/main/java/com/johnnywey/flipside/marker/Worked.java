package com.johnnywey.flipside.marker;

import com.johnnywey.flipside.failable.Fail;

/**
 * Signal an operation went as expected.
 */
public class Worked implements DidItWork {

    @Override
    public Fail getReason() {
        return Fail.SUCCESS;
    }

    @Override
    public String getDetail() {
        return Fail.SUCCESS.name();
    }

    @Override
    public Boolean isSuccess() {
        return true;
    }
}
