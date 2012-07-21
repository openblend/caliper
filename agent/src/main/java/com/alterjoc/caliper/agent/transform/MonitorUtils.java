/*
 * Copyright 2011-2013, KC CLASS; Matej Lazar, Ales Justin.
 */

package com.alterjoc.caliper.agent.transform;

import com.alterjoc.caliper.agent.annotations.ArgsAdapter;
import com.alterjoc.caliper.agent.monitor.MonitorManager;
import com.alterjoc.caliper.agent.monitor.MonitorManagerFactory;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public class MonitorUtils {
    private static ArgsAdapter getArgsAdapter(Class owner, String className) throws Exception {
        if (className == null || className.length() == 0)
            return null;

        return (ArgsAdapter) owner.getClassLoader().loadClass(className).newInstance(); // cache this
    }

    public static void preMonitorStatic(Class clazz, String name, Class[] types, Object[] args, String adapterClass) {
        try {
            MonitorManager manager = MonitorManagerFactory.getInstance();
            ArgsAdapter adapter = getArgsAdapter(clazz, adapterClass);
            if (adapter != null) {
                adapter.preMonitorStatic(manager, clazz, name, types, args);
            } else {
                manager.preMonitorStatic(clazz, name, types, args);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void postMonitorStatic(Class clazz, String name, Class[] types, Object[] args, String adapterClass) {
        try {
            MonitorManager manager = MonitorManagerFactory.getInstance();
            ArgsAdapter adapter = getArgsAdapter(clazz, adapterClass);
            if (adapter != null) {
                adapter.postMonitorStatic(manager, clazz, name, types, args);
            } else {
                manager.postMonitorStatic(clazz, name, types, args);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void preMonitor(Object target, String name, Class[] types, Object[] args, String adapterClass) {
        try {
            MonitorManager manager = MonitorManagerFactory.getInstance();
            ArgsAdapter adapter = getArgsAdapter(target.getClass(), adapterClass);
            if (adapter != null) {
                adapter.preMonitor(manager, target, name, types, args);
            } else {
                manager.preMonitor(target, name, types, args);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void postMonitor(Object target, String name, Class[] types, Object[] args, String adapterClass) {
        try {
            MonitorManager manager = MonitorManagerFactory.getInstance();
            ArgsAdapter adapter = getArgsAdapter(target.getClass(), adapterClass);
            if (adapter != null) {
                adapter.postMonitor(manager, target, name, types, args);
            } else {
                manager.postMonitor(target, name, types, args);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
