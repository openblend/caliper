/*
 * Copyright 2011-2013, KC CLASS; Matej Lazar, Ales Justin.
 */

package com.alterjoc.caliper.iaas;


/**
 * @author Matej Lazar
 */
public interface Compute {

    String scaleUp();

    void scaleDown();

    /**
     * Destroys all instances
     */
    void terminate();
}