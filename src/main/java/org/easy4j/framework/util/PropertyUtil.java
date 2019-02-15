package org.easy4j.framework.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {

    private static final Logger LOGGER = LogManager.getLogger(PropertyUtil.class);

    public static Properties loadProperties(String filename){
       InputStream is = PropertyUtil.class.getClassLoader().getResourceAsStream(filename);

       Properties props = new Properties();

        try {
            props.load(is);
        } catch (IOException e) {
           LOGGER.error("配置文件:{},加载失败", filename, e);
           throw new RuntimeException(e);
        }

        return props;
    }

    public static String getStringValue(Properties props, String key){
        return getStringValue(props, key, "");
    }

    public static String getStringValue(Properties props, String key, String defaultValue){
        return props.getProperty(key, defaultValue);
    }

    public static Integer getIntValue(Properties props, String key){
        return getIntValue(props, key, 0);
    }

    public static Integer getIntValue(Properties props, String key, Integer defaultValue){
        String strValue = props.getProperty(key);
        if(StringUtils.isEmpty(strValue)){
            return defaultValue;
        }
        try {
            return Integer.valueOf(strValue);
        }catch(Exception e){
            LOGGER.error("类型转换错误");
            return defaultValue;
        }
    }

}
