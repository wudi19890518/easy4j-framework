package org.easy4j.framework.helper;

import org.apache.commons.lang3.ArrayUtils;
import org.easy4j.framework.bean.FormParam;
import org.easy4j.framework.bean.Param;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class RequestHelper {

    public static Param createParam(HttpServletRequest request){
        List<FormParam> formParamList = new ArrayList();
        formParamList.addAll(parseParameterNames(request));

        return new Param(formParamList);
    }

    private static List<FormParam> parseParameterNames(HttpServletRequest request){
        List<FormParam> formParamList = new ArrayList<FormParam>();
        Enumeration<String> enumerationNames = request.getParameterNames();
        if(enumerationNames != null){
            while(enumerationNames.hasMoreElements()){
                String fieldName = enumerationNames.nextElement();
                String[] fieldValues = request.getParameterValues(fieldName);
                if(ArrayUtils.isNotEmpty(fieldValues)){
                    Object fieldValue = null;
                    if(fieldValues.length == 1){
                        fieldValue = fieldValues[0];
                    }else{
                        StringBuilder sb = new StringBuilder("");
                        for(int i = 0; i < fieldValues.length; i++){
                            sb.append(fieldValues[i]);
                            if(i != fieldValues.length - 1){
                                sb.append(" ");
                            }
                        }
                        fieldValue = sb.toString();
                    }
                    formParamList.add(new FormParam(fieldName, fieldValue));
                }
            }
        }

        return formParamList;
    }

}
