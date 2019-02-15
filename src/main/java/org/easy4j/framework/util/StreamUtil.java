package org.easy4j.framework.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamUtil {

    private static final Logger LOGGER = LogManager.getLogger(StreamUtil.class);

    public static void copyStream(InputStream inputStream, OutputStream outputStream){

        int length;
        byte[] buffer = new byte[4 * 1024];
        try {
            while((length = inputStream.read(buffer, 0, buffer.length)) != -1){
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
        } catch (IOException e) {
            LOGGER.error("copy stream failure", e);
            throw new RuntimeException(e);
        } finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
               LOGGER.error("close stream failure", e);
            }

        }

    }
}
