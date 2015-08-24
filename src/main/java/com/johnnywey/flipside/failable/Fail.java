package com.johnnywey.flipside.failable;

import java.util.regex.Pattern;

public enum Fail {
    CONNECT_TIMEOUT(504),
    READ_TIMEOUT(598),
    SERVER_MISSING(503),
    NOT_READY(503),
    NOT_FOUND(404),
    BAD_REQUEST(400),
    BAD_RESPONSE(400),
    INVALID_PARAMETERS(400),
    PAYMENT_REQUIRED(402),
    REQUEST_TIMEOUT(408),
    TOO_MANY_REQUESTS(429),
    UNKNOWN(500),
    INTERNAL_ERROR(500),
    ACCESS_DENIED(403),
    ACCEPTED(202),
    SUCCESS(200); // place holder

    private final Integer httpResponseCode;
    private static final Pattern GOOD_RESPONSE = Pattern.compile("20\\d");

    Fail(Integer httpResponseCodeIn) {
        httpResponseCode = httpResponseCodeIn;
    }

    public Integer getHttpResponseCode() {
        return httpResponseCode;
    }

    /**
     * Create one from an existing HTTP status code.
     * <p>
     * <strong>Note</strong>: HTTP status codes of 20x are normalized to 200
     *
     * @param httpResponseCodeIn - The Integer HTTP response code from which to source.
     * @return A new one or null of one cannot be found by response code.
     */
    public static Fail fromHttpResponseCode(final Integer httpResponseCodeIn) {
        if (httpResponseCodeIn == null) {
            return null;
        }

        // If success, return normalized success
        if (GOOD_RESPONSE.matcher(httpResponseCodeIn.toString()).matches()) {
            return SUCCESS;
        }

        for (Fail f : Fail.values()) {
            if (f.getHttpResponseCode().equals(httpResponseCodeIn)) {
                return f;
            }
        }
        return null;
    }

}
