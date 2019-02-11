package org.easy4j.framework.util;

import com.alibaba.fastjson.JSONObject;

/**
 * JSON工具类
 */
public class JsonUtil {

    public static String toJson(Object obj){
        if(obj == null){
            return null;
        }
        return JSONObject.toJSONString(obj);
    }
}
