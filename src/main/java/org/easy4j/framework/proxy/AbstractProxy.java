package org.easy4j.framework.proxy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;

public abstract class AbstractProxy implements Proxy {

    private static final Logger LOGGER = LogManager.getLogger(AbstractProxy.class);

    public Object doProxy(ProxyChain proxyChain) throws Throwable {

        Object result = null;

        Class<?> targetClass = proxyChain.getTargetClass();
        Method targetMethod = proxyChain.getTargetMethod();
        Object[] methodParams = proxyChain.getMethodParams();

        begin();

        try {
            if (intercept(targetClass, targetMethod, methodParams)) {
                before(targetClass, targetMethod, methodParams);
                result = proxyChain.doProxyChain();
                after(targetClass, targetMethod, methodParams);
            } else {
                result = proxyChain.doProxyChain();
            }
        } catch(Exception e){
            LOGGER.error("proxy failure", e);
            error(targetClass, targetMethod, methodParams, e);
            throw e;
        } finally {
            end();
        }

        return result;
    }

    public void begin(){

    }

    public boolean intercept(Class<?> cls, Method method, Object[] params){
        return true;
    }

    public void before(Class<?> cls, Method method, Object[] params){

    }

    public void after(Class<?> cls, Method method, Object[] params){

    }

    public void error(Class<?> cls, Method method, Object[] params, Throwable e){

    }

    public void end(){

    }
}
