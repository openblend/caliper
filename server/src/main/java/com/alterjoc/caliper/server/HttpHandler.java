/*
 * Copyright 2011-2013, KC CLASS; Matej Lazar, Ales Justin.
 */

package com.alterjoc.caliper.server;

import java.io.IOException;

/**
 * Simple http server.
 *
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public interface HttpHandler {
    void handle(HttpContext context) throws IOException;
}
