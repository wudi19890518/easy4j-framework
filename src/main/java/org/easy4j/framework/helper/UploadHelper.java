package org.easy4j.framework.helper;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.easy4j.framework.bean.FileParam;
import org.easy4j.framework.bean.FormParam;
import org.easy4j.framework.bean.Param;
import org.easy4j.framework.util.FileUtil;
import org.easy4j.framework.util.StreamUtil;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 文件上传助手
 */
public class UploadHelper {

    private static final Logger LOGGER = LogManager.getLogger(UploadHelper.class);

    private static ServletFileUpload servletFileUpload;

    public static void init(ServletContext servletContext){
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tmpdir");
        servletFileUpload = new ServletFileUpload(
                                new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD,
                                repository));
        int uploadLimit = ConfigHelper.getAppUploadLimit();
        if(uploadLimit > 0){
            servletFileUpload.setFileSizeMax(uploadLimit * 1024 * 1024);
        }
    }

    public boolean isMultipart(HttpServletRequest request){
        return ServletFileUpload.isMultipartContent(request);
    }

    public Param createParam(HttpServletRequest request){
        List<FormParam> formParamList = new ArrayList<FormParam>();
        List<FileParam> fileParamList = new ArrayList<FileParam>();

        try {
            Map<String, List<FileItem>> fileItemListMap = servletFileUpload.parseParameterMap(request);
            if(MapUtils.isNotEmpty(fileItemListMap)){
                for(Map.Entry<String, List<FileItem>> fileItemEntry : fileItemListMap.entrySet()){
                    String fieldName = fileItemEntry.getKey();
                    List<FileItem> fileItemList = fileItemEntry.getValue();
                    if(CollectionUtils.isNotEmpty(fileItemList)){
                        for(FileItem fileItem : fileItemList){
                            if(fileItem.isFormField()){
                                String fieldValue = fileItem.getString("UTF-8");
                                formParamList.add(new FormParam(fieldName, fieldValue));
                            }else{
                                String fileName = fileItem.getName();
                                if(StringUtils.isNotEmpty(fileName)){
                                    long fileSize = fileItem.getSize();
                                    String contentType = fileItem.getContentType();
                                    InputStream inputStream = fileItem.getInputStream();
                                    fileParamList.add(new FileParam(fieldName, fileName, fileSize,
                                            contentType, inputStream));
                                }
                            }
                        }
                    }
                }
            }


        } catch (FileUploadException e) {
            LOGGER.error("parse file request failure", e);
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("parse file request failure", e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            LOGGER.error("parse file request failure", e);
            throw new RuntimeException(e);
        }

        return new Param(formParamList, fileParamList);
    }

    public static void uploadFile(String basePath, FileParam fileParam){
        if(fileParam != null){
            try {
                String filePath = basePath + fileParam.getFileName();
                FileUtil.createFile(filePath);
                InputStream inputStream = new BufferedInputStream(fileParam.getInputStream());
                OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
                StreamUtil.copyStream(inputStream, outputStream);
            } catch (Exception e) {
                LOGGER.error("upload file failure", e);
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 批量上传
     * @param basePath
     * @param fileParamList
     */
    public static void uploadFile(String basePath, List<FileParam> fileParamList){
        if(CollectionUtils.isNotEmpty(fileParamList)){
            try{
                for(FileParam fileParam : fileParamList){
                    uploadFile(basePath, fileParam);
                }
            }catch(Exception e){
                LOGGER.error("upload file failure", e);
                throw new RuntimeException(e);
            }
        }
    }
}
