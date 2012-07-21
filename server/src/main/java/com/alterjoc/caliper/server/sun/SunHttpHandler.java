/*
 * Copyright 2011-2013, KC CLASS; Matej Lazar, Ales Justin.
 */

package com.alterjoc.caliper.server.sun;

import java.io.IOException;
import java.io.OutputStream;

import com.alterjoc.caliper.server.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
class SunHttpHandler implements com.sun.net.httpserver.HttpHandler {
    private HttpHandler handler;

    SunHttpHandler(HttpHandler handler) {
        this.handler = handler;
    }

    public void handle(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(200, 0);

        OutputStream outputStream = exchange.getResponseBody();
        try {
            handler.handle(new SunHttpContext(exchange));
            outputStream.flush();
        } finally {
            outputStream.close();
        }
    }
}

