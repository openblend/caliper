/*
 * Copyright 2011-2013, KC CLASS, Robert Dukaric, Matej Lazar and Ales Justin.
 */

package com.alterjoc.caliper.agent.monitor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public abstract class AbstractMonitorManager implements MonitorManager {
    private Map<Object, MonitorHandle> handles = new ConcurrentHashMap<Object, MonitorHandle>();

    public void preHandle(Object handleKey, Object... args) {
        MonitorHandle handle = handles.get(handleKey);
        if (handle != null)
            handle.startMonitor(args);
    }

    public void postHandle(Object handleKey, Object... args) {
        MonitorHandle handle = handles.get(handleKey);
        if (handle != null)
            handle.stopMonitor(args);
    }

    public void registerHandle(Object handleKey, MonitorHandle handle) {
        handles.put(handleKey, handle);
    }

    public void unregisterHandle(Object handleKey) {
        handles.remove(handleKey);
    }
}
