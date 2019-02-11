package org.easy4j.framework.helper;

import org.easy4j.framework.util.ClassUtil;

// 初始化各类bean
public class BeanLoader {

    public static void init(){
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };

        for(Class<?> cls : classList){
            ClassUtil.loadClass(cls.getName(), true);
        }
    }
}
