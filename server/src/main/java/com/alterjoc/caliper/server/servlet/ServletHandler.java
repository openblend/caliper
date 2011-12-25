/*
 * Copyright 2011-2013, KC CLASS, Robert Dukaric, Matej Lazar and Ales Justin.
 */

package com.alterjoc.caliper.server.servlet;

import com.alterjoc.caliper.server.HttpContext;
import com.alterjoc.caliper.server.HttpHandler;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public class ServletHandler implements HttpHandler {

    private Servlet servlet;

    public ServletHandler(Servlet servlet) {
        if (servlet == null)
            throw new IllegalArgumentException("Null servlet");
        this.servlet = servlet;
    }

    public void handle(HttpContext context) throws IOException {
        try {
            ServletRequest req = new ServletRequestImpl(context);
            ServletResponse resp = new ServletResponseImpl(context);
            servlet.service(req, resp);
        } catch (ServletException e) {
            throw new IOException(e);
        }
    }
}
