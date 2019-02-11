package org.easy4j.framework.bean;

import java.lang.reflect.Method;

/**
 * web请求处理器
 */
public class Handler {

    private Class<?> controllerClass;

    private Method method;

    public Handler(){}

    public Handler(Class<?> controllerClass, Method method) {
        this.controllerClass = controllerClass;
        this.method = method;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public void setControllerClass(Class<?> controllerClass) {
        this.controllerClass = controllerClass;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
