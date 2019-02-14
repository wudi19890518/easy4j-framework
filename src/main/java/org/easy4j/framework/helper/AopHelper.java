package org.easy4j.framework.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.easy4j.framework.annotation.Aspect;
import org.easy4j.framework.annotation.Service;
import org.easy4j.framework.annotation.Transaction;
import org.easy4j.framework.proxy.AbstractProxy;
import org.easy4j.framework.proxy.Proxy;
import org.easy4j.framework.proxy.ProxyManager;
import org.easy4j.framework.util.ReflectionUtil;

import java.lang.annotation.Annotation;
import java.util.*;

public class AopHelper {

    private static final Logger LOGGER = LogManager.getLogger(AopHelper.class);

    static{
        try {
            // 获取所有父类是AbstractProxy的类,并且带有@Aspect注解
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
            for(Map.Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()){
                Class<?> targetClass = targetEntry.getKey();
                List<Proxy> proxyList = targetEntry.getValue();
                Object proxy = ProxyManager.createProxy(targetClass, proxyList);
                BeanHelper.setBean(targetClass, proxy);
            }
        }catch(Exception e){
            LOGGER.error("aop failure", e);
        }
    }

    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap){
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<Class<?>, List<Proxy>>();
        for(Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : proxyMap.entrySet()){
            Class<?> proxyClass = proxyEntry.getKey();
            Set<Class<?>> targetClassSet = proxyEntry.getValue();
            for(Class<?> targetClass : targetClassSet){
                Proxy instance = (Proxy) ReflectionUtil.newInstance(proxyClass);
                if(targetMap.containsKey(targetClass)){
                    targetMap.get(targetClass).add(instance);
                }else{
                    List<Proxy> proxyList = new ArrayList<Proxy>();
                    proxyList.add(instance);
                    targetMap.put(targetClass, proxyList);
                }
            }

        }
        return targetMap;
    }

    private static void addAspectProxy(Map<Class<?>, Set<Class<?>>> proxyMap){
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AbstractProxy.class);
        for(Class<?> proxyClass : proxyClassSet){
            if(proxyClass.isAnnotationPresent(Aspect.class)){
                Aspect aspect = proxyClass.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
                proxyMap.put(proxyClass, targetClassSet);
            }
        }
    }

    private static void addTransactionProxy(Map<Class<?>, Set<Class<?>>> proxyMap){
        Set<Class<?>> serviceClassSet = ClassHelper.getClassSetByAnnotation(Service.class);
        proxyMap.put(Transaction.class, serviceClassSet);
    }

    private static Map<Class<?>, Set<Class<?>>> createProxyMap() throws Exception{
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<Class<?>, Set<Class<?>>>();
        addAspectProxy(proxyMap);
        addTransactionProxy(proxyMap);
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
