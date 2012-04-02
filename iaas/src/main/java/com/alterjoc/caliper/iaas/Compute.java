package com.alterjoc.caliper.iaas;

import org.jclouds.compute.RunNodesException;

/**
 * @author Matej Lazar
 */
public interface Compute {

    public abstract String createInstance() throws RunNodesException;

    public abstract String scaleUp() throws RunNodesException;

    public abstract void scaleDown()throws Exception;

    /**
     * Destroys all instances
     */
    public abstract void terminate();

}