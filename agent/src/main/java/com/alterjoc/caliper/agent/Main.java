/*
 * Copyright 2011-2013, KC CLASS; Matej Lazar, Ales Justin.
 */

package com.alterjoc.caliper.agent;

import java.lang.instrument.Instrumentation;

import com.alterjoc.caliper.agent.transform.MonitorClassFileTransformer;

/**
 * Main agent class.
 *
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public class Main {
    public static void premain(String agentArgs, Instrumentation inst) {
        MonitorClassFileTransformer mcft = new MonitorClassFileTransformer();
        inst.addTransformer(mcft);
    }
}
