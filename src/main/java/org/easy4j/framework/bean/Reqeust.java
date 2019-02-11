package org.easy4j.framework.bean;

/**
 * web请求信息
 */
public class Reqeust {

    private String requestMethod;

    private String requestPath;

    public Reqeust(){}

    public Reqeust(String requestMethod, String requestPath) {
        this.requestMethod = requestMethod;
        this.requestPath = requestPath;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }
}
