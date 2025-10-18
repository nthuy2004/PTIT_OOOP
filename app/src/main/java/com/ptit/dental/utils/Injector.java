/*
 * PTIT OOP
 * QUAN LY PHONG KHAM RANG
 */
package com.ptit.dental.utils;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Administrator
 */


public class Injector {
    private static final Map<Class<?>, Object> services = new HashMap<>();

    public static <T> void register(Class<T> clazz, T instance) {
        services.put(clazz, instance);
    }

    public static <T> T get(Class<T> clazz) {
        return clazz.cast(services.get(clazz));
    }
}