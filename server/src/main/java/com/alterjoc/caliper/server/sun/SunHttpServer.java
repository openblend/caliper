/*
 * Copyright 2011-2013, KC CLASS, Robert Dukaric, Matej Lazar and Ales Justin.
 */

package com.alterjoc.caliper.server.sun;

import com.alterjoc.caliper.server.HttpHandler;
import com.alterjoc.caliper.server.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public class SunHttpServer implements HttpServer {
    private com.sun.net.httpserver.HttpServer server;

    public void start() throws IOException
    {
        if (server != null)
            server.stop(0);

        InetSocketAddress isa = new InetSocketAddress("localhost", 8080);
        server = com.sun.net.httpserver.HttpServer.create(isa, 0);
        server.setExecutor(Executors.newCachedThreadPool());
        server.start();
    }

    public void addContext(String name, HttpHandler handler)
    {
        if (server != null)
            server.createContext(name, new SunHttpHandler(handler));
    }

    public void removeContext(String name)
    {
        if (server != null)
            server.removeContext(name);
    }

    public void stop()
    {
        com.sun.net.httpserver.HttpServer temp = server;
        server = null;

        if (temp != null)
            temp.stop(0);
    }
}
