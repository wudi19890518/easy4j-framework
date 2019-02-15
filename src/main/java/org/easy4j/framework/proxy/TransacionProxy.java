package org.easy4j.framework.proxy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.easy4j.framework.annotation.Transactional;
import org.easy4j.framework.helper.DatabaseHelper;

import java.lang.reflect.Method;

/**
 * 事务代理
 */
public class TransacionProxy implements Proxy {

    private static final Logger LOGGER = LogManager.getLogger(TransacionProxy.class);

    /**
     * 事务代理方法
     * @param proxyChain
     * @return
     * @throws Throwable
     */
    public Object doProxy(ProxyChain proxyChain) throws Throwable {

        Object result = null;
        Method targetMethod = proxyChain.getTargetMethod();

        if(targetMethod.isAnnotationPresent(Transactional.class)){
            try{
                DatabaseHelper.beginTransaction();
                LOGGER.debug("事务代理开始");
                result = proxyChain.doProxyChain();
                DatabaseHelper.commitTransaction();
                LOGGER.debug("事务代理结束");
            }catch(Exception e){
                DatabaseHelper.rollbackTransaction();
                LOGGER.error("回滚事务", e);
                throw e;
            }
        }else{
            result = proxyChain.doProxyChain();
        }

        return result;
    }

}
