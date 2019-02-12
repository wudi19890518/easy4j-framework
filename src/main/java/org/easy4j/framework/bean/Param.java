package org.easy4j.framework.bean;

import java.util.Map;

/**
 * 请求参数封装
 */
public class Param {

    private Map<String, Object> model;

    public Param(Map<String, Object> model) {
        this.model = model;
    }
}
