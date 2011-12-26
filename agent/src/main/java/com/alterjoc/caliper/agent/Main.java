/*
 * Copyright 2011-2013, KC CLASS, Robert Dukaric, Matej Lazar and Ales Justin.
 */

package com.alterjoc.caliper.agent;

import com.alterjoc.caliper.agent.transform.MonitorClassFileTransformer;

import java.lang.instrument.Instrumentation;

/**
 * Main agent class.
 *
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public class Main {
    public static void premain(String[] args, Instrumentation inst) {
        MonitorClassFileTransformer mcft = new MonitorClassFileTransformer();
        inst.addTransformer(mcft);
    }
}
