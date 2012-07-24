/*
 * Copyright 2011-2013, KC CLASS; Matej Lazar, Ales Justin.
 */

package com.alterjoc.caliper.iaas;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
class Counter {
    static final int LIMIT_SIZE = 5;
    private static Map<String, Counter> counters = new ConcurrentHashMap<String, Counter>();

    private Queue<Long> fifo = new LinkedList<Long>();

    private Counter() {
    }

    synchronized Queue<Long> getFifo() {
        return new LinkedList<Long>(fifo);
    }

    static synchronized Counter getCounter(String appId) {
        Counter counter = counters.get(appId);
        if (counter == null) {
            counter = new Counter();
            counters.put(appId, counter);
        }
        return counter;
    }

    synchronized void start() {
        if (fifo.size() == LIMIT_SIZE) {
            fifo.remove();
        }
        fifo.add(System.currentTimeMillis());
    }

    synchronized void reset() {
        fifo.clear();
    }
}
