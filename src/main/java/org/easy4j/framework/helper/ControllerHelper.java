package org.easy4j.framework.helper;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.easy4j.framework.annotation.RequestMapping;
import org.easy4j.framework.bean.Handler;
import org.easy4j.framework.bean.Request;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ControllerHelper {

    private static final Map<Request, Handler> ACTION_MAP = new HashMap();

    static {
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if(CollectionUtils.isNotEmpty(controllerClassSet)){
            for(Class<?> cls : controllerClassSet){
                Method[] methods = cls.getDeclaredMethods();
                if(ArrayUtils.isNotEmpty(methods)){
                    for(Method method : methods){
                        if(method.isAnnotationPresent(RequestMapping.class)){
                            RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                            String requestPath = requestMapping.value();
                            String requestMethod = requestMapping.method();

                            Request req = new Request(requestMethod, requestPath);

                            Handler handler = new Handler(cls, method);
                            ACTION_MAP.put(req, handler);
                        }
                    }
                }
            }
        }




    }

    public static Handler getHandler(String requestMethod, String requestPath){
        Request req = new Request(requestMethod, requestPath);
        return ACTION_MAP.get(req);
    }



}
