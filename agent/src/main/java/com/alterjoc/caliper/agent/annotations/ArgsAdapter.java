/*
 * Copyright 2011-2013, KC CLASS, Robert Dukaric, Matej Lazar and Ales Justin.
 */

package com.alterjoc.caliper.agent.annotations;

import com.alterjoc.caliper.agent.monitor.MonitorManager;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public interface ArgsAdapter {
    void preMonitorStatic(MonitorManager manager, Class clazz, String name, Class[] types, Object[] args);

    void postMonitorStatic(MonitorManager manager, Class clazz, String name, Class[] types, Object[] args);

    void preMonitor(MonitorManager manager, Object target, String name, Class[] types, Object[] args);

    void postMonitor(MonitorManager manager, Object target, String name, Class[] types, Object[] args);
}
