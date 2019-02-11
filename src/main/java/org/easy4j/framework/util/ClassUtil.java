package org.easy4j.framework.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

public class ClassUtil {

    private static final Logger LOGGER = LogManager.getLogger(ClassUtil.class);


    private static void doAddClass(Set<Class<?>> classSet, String className){
        Class<?> cls = loadClass(className);
        classSet.add(cls);
    }


    private static void addClass(Set<Class<?>> classSet, String dirpathName, String packageName){
        File[] files = new File(dirpathName).listFiles();

        if(ArrayUtils.isNotEmpty(files)){
            for(File file : files){
                String fileName = file.getName();
                if(file.isFile()){
                    if(fileName.endsWith(".class")){
                        String className = packageName + "." + fileName.substring(0, fileName.lastIndexOf("."));
                        doAddClass(classSet, className);
                    }
                }else{
                    String subPackageName = packageName + "." + fileName;
                    String subDirpathName = dirpathName + "/" + fileName;
                    addClass(classSet, subDirpathName, subPackageName);
                }
            }
        }
    }

    private static ClassLoader getClassLoader(){
        return Thread.currentThread().getContextClassLoader();
    }

    private static Class<?> loadClass(String className){
        return loadClass(className, false, getClassLoader());
    }

    private static Class<?> loadClass(String className, boolean initialize, ClassLoader classLoader){
        Class<?> cls = null;
        try {
            cls = Class.forName(className, initialize, classLoader);
        } catch (ClassNotFoundException e) {
            LOGGER.error("load class failure,className:{}", className, e);
            throw new RuntimeException(e);
        }

        return cls;
    }

    /**
     * 扫描类路径下的所有带有Controller,Service注解的类
     * @param basePackage
     * @return
     */
    public static Set<Class<?>> getClassSet(String basePackage){
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        String dirpathName = basePackage.replace(".", "/");

        try {
            Enumeration<URL> urlEnumeration = getClassLoader().getResources(dirpathName);
            if(urlEnumeration != null){
                while(urlEnumeration.hasMoreElements()){
                    URL url = urlEnumeration.nextElement();
                    String protocol = url.getProtocol();

                    if(protocol.equals("file")){
                        String file = url.getFile();
                        addClass(classSet, file, basePackage);
                    }
                }
            }

        } catch (IOException e) {
           LOGGER.error("scan class failure,basePackage:{}", basePackage, e);
           throw new RuntimeException(e);
        }


        return classSet;
    }
}
