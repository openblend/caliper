/*
 * Copyright 2011-2013, KC CLASS; Matej Lazar, Ales Justin.
 */

package com.alterjoc.caliper.iaas;

import org.jclouds.compute.RunNodesException;

/**
 * @author Matej Lazar
 */
public interface Compute {
    String createInstance() throws RunNodesException;

    String scaleUp() throws RunNodesException;

    void scaleDown() throws Exception;

    /**
     * Destroys all instances
     */
    void terminate();
}