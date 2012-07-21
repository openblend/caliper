/*
 * Copyright 2011-2013, KC CLASS, Robert Dukaric, Matej Lazar and Ales Justin.
 */

package com.alterjoc.caliper.server;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Servlet;

import com.alterjoc.caliper.agent.annotations.Monitor;
import com.alterjoc.caliper.server.servlet.ServletHandler;
import com.alterjoc.caliper.server.xml.Parser;
import com.alterjoc.caliper.server.xml.WebXmlMetaData;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
final class AppFactory {

    private static final FileFilter JARS_FILTER = new FileFilter() {
        public boolean accept(File pathname) {
            return pathname.getName().endsWith(".jar");
        }
    };

    private final HttpServer server;

    AppFactory(HttpServer server) {
        this.server = server;
    }

    @SuppressWarnings("deprecation")
    @Monitor
    void createApp(String app) throws Exception {
        File war = new File(app);
        if (war.exists() == false)
            throw new IllegalArgumentException("No such app: " + war);
        if (war.isDirectory() == false)
            throw new IllegalArgumentException("Can only handle exploded apps: " + war);

        File webXml = new File(war, "WEB-INF/web.xml");
        if (webXml.exists() == false)
            throw new IllegalArgumentException("No web.xml in app: " + war);

        WebXmlMetaData wxmd = Parser.parseWebXml(new FileInputStream(webXml));

        List<URL> roots = new ArrayList<URL>();
        File classes = new File(war, "WEB-INF/classes");
        if (classes.exists())
            roots.add(classes.toURL());
        File libs = new File(war, "WEB-INF/lib");
        if (libs.exists()) {
            File[] jars = libs.listFiles(JARS_FILTER);
            for (File jar : jars)
                roots.add(jar.toURL());
        }

        ClassLoader cl = new URLClassLoader(roots.toArray(new URL[roots.size()]));
        for (String name : wxmd.names()) {
            String className = wxmd.getServlet(name);
            String mapping = wxmd.getMapping(name);
            Servlet servlet = (Servlet) cl.loadClass(className).newInstance();
            server.addContext(mapping, new ServletHandler(servlet));
        }

        System.out.println("\nAdd new app: " + app + "\n" + wxmd);
    }
}
