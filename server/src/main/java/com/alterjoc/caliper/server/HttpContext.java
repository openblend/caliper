/*
 * Copyright 2011-2013, KC CLASS; Matej Lazar, Ales Justin.
 */

package com.alterjoc.caliper.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

/**
 * Simple http server.
 *
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public interface HttpContext {

    String getParameter(String name);

    Set<String> getParameterNames();

    InputStream getInputStream() throws IOException;

    OutputStream getOutputStream() throws IOException;

    Object getAttribute(String name);

    Set<String> getAttributeNames();

    void setAttribute(String name, Object o);

    void removeAttribute(String name);
}
