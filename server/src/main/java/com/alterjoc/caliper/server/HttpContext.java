/*
 * Copyright 2011-2013, KC CLASS, Robert Dukaric, Matej Lazar and Ales Justin.
 */

package com.alterjoc.caliper.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Simple http server.
 *
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public interface HttpContext {
    InputStream getInputStream() throws IOException;

    OutputStream getOutputStream() throws IOException;
}
