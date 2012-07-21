/*
 * Copyright 2011-2013, KC CLASS; Matej Lazar, Ales Justin.
 */

package com.alterjoc.caliper.server.sun;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.alterjoc.caliper.agent.annotations.Monitor;
import com.alterjoc.caliper.server.HttpHandler;
import com.alterjoc.caliper.server.HttpServer;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public class SunHttpServer implements HttpServer {
    private com.sun.net.httpserver.HttpServer server;

    public void start() throws IOException {
        if (server != null)
            server.stop(0);

        InetSocketAddress isa = new InetSocketAddress("localhost", 8080);
        server = com.sun.net.httpserver.HttpServer.create(isa, 0);
        server.setExecutor(Executors.newCachedThreadPool());
        server.start();
    }

    @Monitor
    public void addContext(String mapping, HttpHandler handler) {
        if (server != null)
            server.createContext(mapping, new SunHttpHandler(handler));
    }

    @Monitor
    public void removeContext(String mapping) {
        if (server != null)
            server.removeContext(mapping);
    }

    public void stop() {
        com.sun.net.httpserver.HttpServer temp = server;
        server = null;

        if (temp != null)
            temp.stop(0);
    }
}
