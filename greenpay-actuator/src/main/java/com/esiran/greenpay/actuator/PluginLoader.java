package com.esiran.greenpay.actuator;

import sun.plugin.security.PluginClassLoader;

import java.lang.reflect.Type;

public class PluginLoader {
    public static <T> Plugin<T> loadForClassPath(String classPath) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<T> pluginClass = (Class<T>) ClassLoader.getSystemClassLoader().loadClass(classPath);
        Type type = pluginClass.getSuperclass();
        Plugin<T> t = (Plugin<T>) pluginClass.newInstance();
        return t;
    }
}
