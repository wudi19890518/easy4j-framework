package org.easy4j.framework.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.easy4j.framework.annotation.Aspect;
import org.easy4j.framework.proxy.AbstractProxy;
import org.easy4j.framework.proxy.Proxy;

import java.lang.annotation.Annotation;
import java.util.*;

public class AopHelper {

    private static final Logger LOGGER = LogManager.getLogger(AopHelper.class);

    static{
        try {
            // 获取所有父类是AbstractProxy的类,并且带有@Aspect注解
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
        }catch(Exception e){
            LOGGER.error("aop failure", e);
        }


        // 获取
    }

    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap){
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<Class<?>, List<Proxy>>();

        for(Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : proxyMap.entrySet()){
            Class<?> proxyClass = proxyEntry.getKey();
            Set<Class<?>> targetClassSet = proxyEntry.getValue();

        }


        return targetMap;
    }

    private static Map<Class<?>, Set<Class<?>>> createProxyMap() throws Exception{
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<Class<?>, Set<Class<?>>>();
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AbstractProxy.class);
        for(Class<?> proxyClass : proxyClassSet){
            if(proxyClass.isAnnotationPresent(Aspect.class)){
                Aspect aspect = proxyClass.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
                proxyMap.put(proxyClass, targetClassSet);
            }
        }
        return proxyMap;
    }

    private static Set<Class<?>> createTargetClassSet(Aspect aspect){
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        Class<? extends Annotation> annotation = aspect.value();
        if(annotation != null && !annotation.equals(Aspect.class)){
            classSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
        }
        return classSet;

    }


}
