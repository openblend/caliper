/*
 * Copyright 2011-2013, KC CLASS, Robert Dukaric, Matej Lazar and Ales Justin.
 */

package com.alterjoc.caliper.agent.monitor;

import java.util.ServiceLoader;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public final class MonitorManagerFactory {
    private static volatile MonitorManager instance;

    public static MonitorManager getInstance() {
        if (instance == null) {
            synchronized (MonitorManagerFactory.class) {
                if (instance == null) {
                    instance = ServiceLoader.load(MonitorManager.class).iterator().next();
                }
            }
        }
        return instance;
    }
}
