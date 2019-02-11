package org.easy4j.framework.helper;

import org.easy4j.framework.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BeanHelper {

    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap();

    static {
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for(Class<?> beanClass : beanClassSet){
            Object instance = ReflectionUtil.newInstance(beanClass);
            BEAN_MAP.put(beanClass, instance);
        }
    }

    public static Map<Class<?>, Object> getBeanMap(){
        return BEAN_MAP;
    }

    public static Object getBean(Class<?> cls){
        if(!BEAN_MAP.containsKey(cls)){
            return new RuntimeException("can not get bean class:" + cls);
        }

        return BEAN_MAP.get(cls);
    }


}
