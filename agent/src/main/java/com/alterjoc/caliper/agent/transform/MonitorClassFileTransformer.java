/*
 * Copyright 2011-2013, KC CLASS, Robert Dukaric, Matej Lazar and Ales Justin.
 */

package com.alterjoc.caliper.agent.transform;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.Modifier;
import java.security.ProtectionDomain;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import com.alterjoc.caliper.agent.annotations.ArgsAdapter;
import com.alterjoc.caliper.agent.annotations.Monitor;
import com.alterjoc.caliper.agent.annotations.NoopArgsAdapter;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.LoaderClassPath;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public class MonitorClassFileTransformer implements ClassFileTransformer {

    private Map<ClassLoader, ClassPool> pools = new WeakHashMap<ClassLoader, ClassPool>();

    protected synchronized ClassPool getClassPool(ClassLoader cl) {
        ClassPool pool = pools.get(cl);
        if (pool == null) {
            pool = new ClassPool();
            pool.appendClassPath(new LoaderClassPath(cl));
            pools.put(cl, pool);
        }
        return pool;
    }

    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (loader == null)
            return classfileBuffer;

        try {
            final CtClass clazz = getClassPool(loader).makeClass(new ByteArrayInputStream(classfileBuffer));
            modifyClass(clazz);
            return clazz.toBytecode();
        } catch (Exception e) {
            throw new IllegalClassFormatException(e.getMessage());
        }
    }

    protected void modifyClass(CtClass clazz) throws Exception {
        Set<CtMethod> methods = new HashSet<CtMethod>();
        if (clazz.hasAnnotation(Monitor.class)) {
            methods.addAll(Arrays.asList(clazz.getMethods()));
        } else {
            getAnnotatedMethods(methods, clazz);
        }

        for (CtMethod m : methods) {
            if (Modifier.isStatic(m.getModifiers())) {
                m.insertBefore(MonitorUtils.class.getName() + ".preMonitorStatic($class, " + quote(m.getName()) + ", $sig, $args, " + getAdapterClassName(m) + ");");
                m.insertAfter(MonitorUtils.class.getName() + ".postMonitorStatic($class, " + quote(m.getName()) + ", $sig, $args, " + getAdapterClassName(m) + ");", true);
            } else {
                m.insertBefore(MonitorUtils.class.getName() + ".preMonitor($0, " + quote(m.getName()) + ", $sig, $args, " + getAdapterClassName(m) + ");");
                m.insertAfter(MonitorUtils.class.getName() + ".postMonitor($0, " + quote(m.getName()) + ", $sig, $args, " + getAdapterClassName(m) + ");", true);
            }
        }
    }

    protected void getAnnotatedMethods(Set<CtMethod> methods, CtClass clazz) throws Exception {
        if (clazz == null)
            return;

        for (CtMethod cm : clazz.getDeclaredMethods()) {
            if (cm.hasAnnotation(Monitor.class))
                methods.add(cm); // TODO -- dups from override, etc
        }

        getAnnotatedMethods(methods, clazz.getSuperclass());
    }

    private static String getAdapterClassName(CtMethod m) throws Exception {
        Monitor monitor = (Monitor) m.getAnnotation(Monitor.class);
        Class<? extends ArgsAdapter> adapterClass = monitor.adapter();
        String returnString = (adapterClass.equals(NoopArgsAdapter.class)) ? "" : adapterClass.getName();
        return quote(returnString);
    }

    private static String quote(String returnString) {
        return "\"" + returnString + "\"";
    }
}
