package org.easy4j.framework.helper;

import org.easy4j.framework.ConfigConstant;
import org.easy4j.framework.util.PropertyUtil;

import java.util.Properties;

public class ConfIgHelper {

    private static final Properties CONFIG_PROPERTIES;

    static {
        CONFIG_PROPERTIES = PropertyUtil.loadProperties(ConfigConstant.CONFIG_FILE_NAME);
    }

    public static String getAppBasePackage(){
        return PropertyUtil.getStringValue(CONFIG_PROPERTIES, ConfigConstant.APP_BASE_BACKPAGE);
    }

}
