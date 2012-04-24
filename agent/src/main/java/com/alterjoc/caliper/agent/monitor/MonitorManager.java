/*
 * Copyright 2011-2013, KC CLASS, Robert Dukaric, Matej Lazar and Ales Justin.
 */

package com.alterjoc.caliper.agent.monitor;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public interface MonitorManager {
    void preMonitorStatic(Class clazz, String name, Class[] types, Object[] args);

    void postMonitorStatic(Class clazz, String name, Class[] types, Object[] args);

    void preMonitor(Object target, String name, Class[] types, Object[] args);

    void postMonitor(Object target, String name, Class[] types, Object[] args);

    void preHandle(Object handle, Object... args);

    void postHandle(Object handle, Object... args);

    void registerHandle(Object handleKey, MonitorHandle handle);

    void unregisterHandle(Object handleKey);
}
