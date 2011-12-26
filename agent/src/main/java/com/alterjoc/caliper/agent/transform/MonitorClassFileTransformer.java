/*
 * Copyright 2011-2013, KC CLASS, Robert Dukaric, Matej Lazar and Ales Justin.
 */

package com.alterjoc.caliper.agent.transform;

import com.alterjoc.caliper.agent.annotations.Monitor;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.LoaderClassPath;

import java.io.ByteArrayInputStream;
import java.lang.annotation.Annotation;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public class MonitorClassFileTransformer implements ClassFileTransformer {

    private Map<ClassLoader, ClassPool> pools = new WeakHashMap<ClassLoader, ClassPool>();

    protected synchronized ClassPool getClassPool(ClassLoader cl) {
        ClassPool pool = pools.get(cl);
        if (pool == null) {
            pool = new ClassPool(ClassPool.getDefault());
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
        if (clazz.hasAnnotation(Monitor.class)) {
            // TODO
        } else {
            // TODO
        }
    }

    protected Iterable<CtMethod> getAnnotatedMethods(CtClass clazz, Class<? extends Annotation> annotationClass) {
        Set<CtMethod> methods = new HashSet<CtMethod>();
        for (CtMethod cm : clazz.getDeclaredMethods()) {
            if (cm.hasAnnotation(annotationClass))
                methods.add(cm); // TODO -- dups from override, etc
        }
        return methods;
    }
}
