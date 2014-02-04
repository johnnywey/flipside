package com.johnnywey.flipside;

/**
 * Simple interface for an HttpResponse of some sort to allow matching.
 *
 * See {@link com.johnnywey.flipside.Matcher}
 */
public interface ClientResponse {
    public Integer getStatusCode();
    public String getStatusText();
}
