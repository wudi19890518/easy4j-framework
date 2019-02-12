package org.easy4j.framework.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionUtil {

    private static final Logger LOGGER = LogManager.getLogger(ReflectionUtil.class);

    public static Object newInstance(Class<?> beanClass){
        Object instance = null;
        try {
            instance = beanClass.newInstance();
        } catch (Exception e) {
            LOGGER.error("new instance failure:{}", beanClass, e);
            throw new RuntimeException(e);
        }
        return instance;
    }

    public static Object invokeMethod(Method method, Object instance, Object params){
        Object result = null;

        try {
            result = method.invoke(instance, params);
        } catch (Exception e) {
            LOGGER.error("invoke method failure,method:{}", method, e);
            throw new RuntimeException(e);
        }

        return result;
    }


    public static void setField(Field beanField, Object beanInstance, Object beanFieldValue){
        try {
            beanField.setAccessible(true);
            beanField.set(beanInstance, beanFieldValue);
        } catch (IllegalAccessException e) {
            LOGGER.error("set field failure", e);
            throw new RuntimeException(e);
        }
    }
}
