/*
 * Copyright 2011-2013, KC CLASS; Matej Lazar, Ales Justin.
 */

package com.alterjoc.caliper.noop;

import java.util.Arrays;
import java.util.logging.Logger;

import com.alterjoc.caliper.agent.monitor.AbstractMonitorManager;
import com.alterjoc.caliper.agent.monitor.MonitorManager;
import org.kohsuke.MetaInfServices;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
@MetaInfServices(MonitorManager.class)
public class NoopMonitorManager extends AbstractMonitorManager {
    private static Logger log = Logger.getLogger(NoopMonitorManager.class.getName());

    public void preMonitorStatic(Class clazz, String name, Class[] types, Object[] args) {
        log("Pre", clazz, name, types, args);
    }

    public void postMonitorStatic(Class clazz, String name, Class[] types, Object[] args) {
        log("Post", clazz, name, types, args);
    }

    public void preMonitor(Object target, String name, Class[] types, Object[] args) {
        log("Pre", target, name, types, args);
    }

    public void postMonitor(Object target, String name, Class[] types, Object[] args) {
        log("Post", target, name, types, args);
    }

    protected static void log(String phase, Object target, String name, Class[] types, Object[] args) {
        log.info(phase + " - " + target + "::" + name + Arrays.toString(args));
    }
}
