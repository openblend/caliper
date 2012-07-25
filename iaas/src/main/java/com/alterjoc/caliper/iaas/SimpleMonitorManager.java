/*
 * Copyright 2011-2013, KC CLASS; Matej Lazar, Ales Justin.
 */

package com.alterjoc.caliper.iaas;

import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

import com.alterjoc.caliper.agent.monitor.AbstractMonitorManager;
import com.alterjoc.caliper.agent.monitor.MonitorManager;
import org.kohsuke.MetaInfServices;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
@MetaInfServices(MonitorManager.class)
public class SimpleMonitorManager extends AbstractMonitorManager {
    private static final String SINGLE_APP = "SINGLE_APP";
    private static final String APP_FACTORY = "com.alterjoc.caliper.server.AppFactory";
    private static final String CREATE_APP = "createApp";
    private static final String SERVLET_HANDLER = "com.alterjoc.caliper.server.servlet.ServletHandler";

    private Set<String> apps;
    private Compute compute;

    public SimpleMonitorManager() {
        apps = new HashSet<String>();
        compute = new LoggingCompute(apps);
    }

    private static boolean isCreateApp(Class clazz, String name) {
        return APP_FACTORY.equals(clazz.getName()) && CREATE_APP.equals(name);
    }

    public void preMonitorStatic(Class clazz, String name, Class[] types, Object[] args) {
        if (isCreateApp(clazz, name)) {
            apps.add(args[0].toString());
        } else if (SERVLET_HANDLER.equals(clazz.getName())) {
            Counter counter = Counter.getCounter(SINGLE_APP);
            counter.start();
        }
    }

    public void postMonitorStatic(Class clazz, String name, Class[] types, Object[] args) {
        if (SERVLET_HANDLER.equals(clazz.getName())) {
            Counter counter = Counter.getCounter(SINGLE_APP);
            Queue<Long> fifo = counter.getFifo();
            int size = fifo.size();
            Long[] ts = fifo.toArray(new Long[size]);
            if (size > 1) {
                long diff = ts[size - 1] - ts[0];
                if (size == Counter.LIMIT_SIZE && (diff < 1000 * 60)) {
                    // less than 1min?
                    compute.scaleUp();
                    counter.reset();
                } else if (diff > 1000 * 60 * 5) {
                    // more than 5min
                    compute.scaleDown();
                    counter.reset();
                }
            }
        }
    }

    public void preMonitor(Object target, String name, Class[] types, Object[] args) {
        preMonitorStatic(target.getClass(), name, types, args);
    }

    public void postMonitor(Object target, String name, Class[] types, Object[] args) {
        postMonitorStatic(target.getClass(), name, types, args);
    }
}
