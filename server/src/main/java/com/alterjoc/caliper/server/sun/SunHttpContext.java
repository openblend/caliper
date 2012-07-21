/*
 * Copyright 2011-2013, KC CLASS; Matej Lazar, Ales Justin.
 */

package com.alterjoc.caliper.server.sun;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.CopyOnWriteArraySet;

import com.alterjoc.caliper.server.HttpContext;
import com.sun.net.httpserver.HttpExchange;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
class SunHttpContext implements HttpContext {
    private HttpExchange exchange;
    private Map<String, String> parameters;
    private Set<String> attributeNames = new CopyOnWriteArraySet<String>();

    SunHttpContext(HttpExchange exchange) {
        this.exchange = exchange;
    }

    protected Map<String, String> getParameters() {
        if (parameters == null) {
            Map<String, String> map = new HashMap<String, String>();
            String query = exchange.getRequestURI().getQuery();
            if (query != null) {
                StringTokenizer tokenizer = new StringTokenizer(query, "=&");
                while (tokenizer.hasMoreTokens()) {
                    String name = tokenizer.nextToken();
                    String value = tokenizer.nextToken();
                    map.put(name, value);
                }
            }
            parameters = map;
        }
        return parameters;
    }

    public String getParameter(String name) {
        return getParameters().get(name);
    }

    public Set<String> getParameterNames() {
        return getParameters().keySet();
    }

    public InputStream getInputStream() throws IOException {
        return exchange.getRequestBody();
    }

    public OutputStream getOutputStream() throws IOException {
        return exchange.getResponseBody();
    }

    public Object getAttribute(String name) {
        return exchange.getAttribute(name);
    }

    public Set<String> getAttributeNames() {
        return Collections.unmodifiableSet(attributeNames);
    }

    public void setAttribute(String name, Object o) {
        exchange.setAttribute(name, o);
        attributeNames.add(name);
    }

    public void removeAttribute(String name) {
        attributeNames.remove(name);
        exchange.setAttribute(name, null);
    }
}
