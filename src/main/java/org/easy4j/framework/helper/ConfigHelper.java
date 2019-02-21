package org.easy4j.framework.helper;

import org.easy4j.framework.ConfigConstant;
import org.easy4j.framework.util.CastUtil;
import org.easy4j.framework.util.PropertyUtil;

import java.util.Properties;

/**
 * 配置文件工具类
 */
public class ConfigHelper {

    private static final Properties CONFIG_PROPERTIES;

    static {
        CONFIG_PROPERTIES = PropertyUtil.loadProperties(ConfigConstant.CONFIG_FILE_NAME);
    }

    public static String getAppBasePackage(){
        return PropertyUtil.getStringValue(CONFIG_PROPERTIES, ConfigConstant.APP_BASE_BACKPAGE);
    }

    public static String getAppViewPath(){
        return PropertyUtil.getStringValue(CONFIG_PROPERTIES, ConfigConstant.APP_VIEW_PATH);
    }

    public static String getJdbcDriver(){
        return PropertyUtil.getStringValue(CONFIG_PROPERTIES, ConfigConstant.APP_JDBC_DRIVER);
    }

    public static String getJdbcUrl(){
        return PropertyUtil.getStringValue(CONFIG_PROPERTIES, ConfigConstant.APP_JDBC_URL);
    }

    public static String getJdbcUsername(){
        return PropertyUtil.getStringValue(CONFIG_PROPERTIES, ConfigConstant.APP_JDBC_USERNAME);
    }

    public static String getJdbcPassword(){
        return PropertyUtil.getStringValue(CONFIG_PROPERTIES, ConfigConstant.APP_JDBC_PASSWORD);
    }

    public static int getAppUploadLimit(){
        return PropertyUtil.getIntValue(CONFIG_PROPERTIES, ConfigConstant.APP_UPLOAD_LIMIT);
    }

    public static String getString(String key){
        return PropertyUtil.getStringValue(CONFIG_PROPERTIES, key);
    }

    public static boolean getBoolean(String key){
        return CastUtil.toBoolean(PropertyUtil.getStringValue(CONFIG_PROPERTIES, key, "false"));
    }

}
