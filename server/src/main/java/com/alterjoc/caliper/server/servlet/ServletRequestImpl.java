/*
 * Copyright 2011-2013, KC CLASS, Robert Dukaric, Matej Lazar and Ales Justin.
 */

package com.alterjoc.caliper.server.servlet;

import com.alterjoc.caliper.server.HttpContext;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public class ServletRequestImpl implements ServletRequest {

    private HttpContext context;

    public ServletRequestImpl(HttpContext context) {
        if (context == null)
            throw new IllegalArgumentException("Null context");
        this.context = context;
    }

    public String getCharacterEncoding() {
        return "UTF-8";
    }

    public void setCharacterEncoding(String env) throws UnsupportedEncodingException {
    }

    public int getContentLength() {
        return -1;
    }

    public String getContentType() {
        return "html/text";
    }

    public ServletInputStream getInputStream() throws IOException {
        return new ServletInputStream() {
            public int read() throws IOException {
                return context.getInputStream().read();
            }
        };
    }

    public String getParameter(String name) {
        return context.getParameter(name);
    }

    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(context.getParameterNames());
    }

    public String[] getParameterValues(String name) {
        return new String[]{getParameter(name)};
    }

    public Map<String, String[]> getParameterMap() {
        return Collections.emptyMap();
    }

    public String getProtocol() {
        return null;
    }

    public String getScheme() {
        return null;
    }

    public String getServerName() {
        return null;
    }

    public int getServerPort() {
        return 8080;
    }

    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    public String getRemoteAddr() {
        return null;
    }

    public String getRemoteHost() {
        return null;
    }

    public Object getAttribute(String name) {
        return context.getAttribute(name);
    }

    public Enumeration<String> getAttributeNames() {
        return Collections.enumeration(context.getAttributeNames());
    }

    public void setAttribute(String name, Object o) {
        context.setAttribute(name, o);
    }

    public void removeAttribute(String name) {
        context.removeAttribute(name);
    }

    public Locale getLocale() {
        return Locale.getDefault();
    }

    public Enumeration<Locale> getLocales() {
        return Collections.enumeration(Collections.<Locale>emptySet());
    }

    public boolean isSecure() {
        return false;
    }

    public RequestDispatcher getRequestDispatcher(String path) {
        return null;
    }

    public String getRealPath(String path) {
        return null;
    }

    public int getRemotePort() {
        return 8080;
    }

    public String getLocalName() {
        return null;
    }

    public String getLocalAddr() {
        return null;
    }

    public int getLocalPort() {
        return 8080;
    }

    public ServletContext getServletContext() {
        return null; // TODO
    }

    public AsyncContext startAsync() throws IllegalStateException {
        return null;
    }

    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
        return null;
    }

    public boolean isAsyncStarted() {
        return false;
    }

    public boolean isAsyncSupported() {
        return false;
    }

    public AsyncContext getAsyncContext() {
        return null;
    }

    public DispatcherType getDispatcherType() {
        return null;
    }
}
