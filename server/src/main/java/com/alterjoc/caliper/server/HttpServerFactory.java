/*
 * Copyright 2011-2013, KC CLASS; Matej Lazar, Ales Justin.
 */

package com.alterjoc.caliper.server;

import com.alterjoc.caliper.server.sun.SunHttpServer;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
final class HttpServerFactory {

    static HttpServer createHttpServer() {
        return new SunHttpServer();
    }

}
