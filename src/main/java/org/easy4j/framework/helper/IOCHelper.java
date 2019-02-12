package org.easy4j.framework.helper;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.easy4j.framework.annotation.Inject;
import org.easy4j.framework.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

public class IOCHelper {

    static {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();

        if(MapUtils.isNotEmpty(beanMap)){
            for(Map.Entry<Class<?>, Object> entry : beanMap.entrySet()){
                Class<?> cls = entry.getKey();
                Object instance = entry.getValue();

                Field[] beanFields = cls.getDeclaredFields();
                if(ArrayUtils.isNotEmpty(beanFields)){
                    for(Field beanField : beanFields){
                        if(beanField.isAnnotationPresent(Inject.class)){
                            Class<?> beanType = beanField.getType();
                            Object beanFieldInstance = beanMap.get(beanType);
                            if(beanFieldInstance != null){
                                ReflectionUtil.setField(beanField, instance, beanFieldInstance);
                            }
                        }
                    }
                }
            }

        }
    }
}
