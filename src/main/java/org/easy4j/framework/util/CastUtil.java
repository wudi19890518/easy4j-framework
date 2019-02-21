package org.easy4j.framework.util;

public class CastUtil {

    public static String toString(Object value, String defaultValue){
       if(value == null){
            return defaultValue;
       }

       return value.toString();

    }

    public static String toString(Object value){
        return toString(value, "");
    }

    public static boolean toBoolean(String value){
        return Boolean.valueOf(value);
    }
}
