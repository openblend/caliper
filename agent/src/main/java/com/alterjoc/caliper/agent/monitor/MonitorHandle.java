/*
 * Copyright 2011-2013, KC CLASS; Matej Lazar, Ales Justin.
 */

package com.alterjoc.caliper.agent.monitor;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public interface MonitorHandle {
    void startMonitor(Object... args);

    void stopMonitor(Object... args);
}
