/*
 * Copyright 2011-2013, KC CLASS, Robert Dukaric, Matej Lazar and Ales Justin.
 */

package com.alterjoc.caliper.server.sun;

import com.alterjoc.caliper.server.HttpContext;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
class SunHttpContext implements HttpContext {
    private HttpExchange exchange;

    SunHttpContext(HttpExchange exchange) {
        this.exchange = exchange;
    }

    public InputStream getInputStream() throws IOException {
        return exchange.getRequestBody();
    }

    public OutputStream getOutputStream() throws IOException {
        return exchange.getResponseBody();
    }
}
