/*
 * Copyright 2011-2013, KC CLASS; Matej Lazar, Ales Justin.
 */

package com.alterjoc.caliper.iaas;

import java.util.Set;
import java.util.logging.Logger;

import org.jclouds.compute.RunNodesException;

/**
 * @author Ales Justin
 */
class LoggingCompute implements Compute {
    private static final Logger log = Logger.getLogger(LoggingCompute.class.getName());
    private Set<String> apps;

    LoggingCompute(Set<String> apps) {
        this.apps = apps;
    }

    public String createInstance() throws RunNodesException {
        return "<noop>";
    }

    public String scaleUp() {
        log.info("Scale up -- " + apps + " !!!");
        return "<noop>";
    }

    public void scaleDown() {
        log.info("Scale down -- " + apps + " !!!");
    }

    public void terminate() {
    }
}