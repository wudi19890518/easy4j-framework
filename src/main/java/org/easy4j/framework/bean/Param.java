package org.easy4j.framework.bean;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 请求参数封装
 */
public class Param {

    private List<FormParam> formParamList;

    private List<FileParam> fileParamList;

    public Param(List<FormParam> formParamList) {
        this.formParamList = formParamList;
    }

    public Param(List<FormParam> formParamList, List<FileParam> fileParamList) {
        this.formParamList = formParamList;
        this.fileParamList = fileParamList;
    }

    public Map<String, Object> getFieldMap(){
        Map<String, Object> fieldMap = new HashMap();
        if(CollectionUtils.isNotEmpty(formParamList)){
            for(FormParam formParam : formParamList){
                String fieldName = formParam.getFieldName();
                Object fieldValue = formParam.getFieldValue();
                if(fieldMap.containsKey(fieldName)){
                    fieldValue = fieldMap.get(fieldName) + " " + fieldValue;
                }
                fieldMap.put(fieldName, fieldValue);
            }
        }
        return fieldMap;
    }

    public Map<String, List<FileParam>> getFileMap(){
        Map<String, List<FileParam>> fileMap = new HashMap<String, List<FileParam>>();
        if(CollectionUtils.isNotEmpty(fileParamList)){
           for(FileParam fileParam : fileParamList){
                String fieldName = fileParam.getFieldName();
                if(fileMap.containsKey(fieldName)){
                    fileMap.get(fieldName).add(fileParam);
                }else{
                    List<FileParam> fileParams = new ArrayList<FileParam>();
                    fileParams.add(fileParam);
                    fileMap.put(fieldName, fileParams);
                }

           }
        }
        return fileMap;
    }

    public List<FileParam> getFileList(String fieldName){
        return getFileMap().get(fieldName);
    }

    public FileParam getFile(String fieldName){
        List<FileParam> fileParamList = getFileList(fieldName);
        if(CollectionUtils.isNotEmpty(fileParamList)
                && fileParamList.size() == 1){
            return fileParamList.get(0);
        }

        return null;
    }

    public boolean isEmpty(){
        return CollectionUtils.isEmpty(fileParamList)
                && CollectionUtils.isEmpty(formParamList);
    }
}
