/*
 * Copyright 2011-2013, KC CLASS, Robert Dukaric, Matej Lazar and Ales Justin.
 */

package com.alterjoc.caliper.server;

/**
 * Caliper server main.
 *
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public class Main {
    public static void main(String[] args) {
        HttpServer server = HttpServerFactory.createHttpServer();
        Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownHook(server)));
        try {
            // start server
            server.start();
            // create apps
            AppFactory appFactory = new AppFactory(server);
            for (String app : args) {
                appFactory.createApp(app);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class ShutdownHook implements Runnable {
        HttpServer server;

        private ShutdownHook(HttpServer server) {
            this.server = server;
        }

        public void run() {
            HttpServer tmp = server;
            server = null;
            tmp.stop();
        }
    }
}
