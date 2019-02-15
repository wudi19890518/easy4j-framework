package org.easy4j.framework.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

/**
 * 文件工具类
 */
public final class FileUtil {

    private static final Logger LOGGER = LogManager.getLogger(FileUtil.class);

    public static String getRealFileName(String fileName){
        return FilenameUtils.getName(fileName);
    }

    public static File createFile(String filePath){
        File file = null;
        try {
            file = new File(filePath);
            File parentFile = file.getParentFile();
            if(!parentFile.exists()){
                FileUtils.forceMkdir(parentFile);
            }
        } catch (IOException e) {
            LOGGER.error("create file failure", e);
            throw new RuntimeException(e);
        }

        return file;
    }


}
