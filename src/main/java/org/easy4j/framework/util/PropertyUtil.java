package org.easy4j.framework.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {

    private static final Logger LOGGER = LogManager.getLogger(PropertyUtil.class);

    public static Properties loadProperties(String filename){
       InputStream is = Thread.currentThread().getClass().getClassLoader().getResourceAsStream(filename);

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

}
