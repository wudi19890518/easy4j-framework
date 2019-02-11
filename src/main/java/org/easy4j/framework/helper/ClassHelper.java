package org.easy4j.framework.helper;

import org.easy4j.framework.util.ClassUtil;

import java.util.Set;

public class ClassHelper {


    private static final Set<Class<?>> CLASS_SET;

    static {
        String appBasePackage = ConfIgHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(appBasePackage);
    }
}
