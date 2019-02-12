package org.easy4j.framework.core.servlet;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.easy4j.framework.annotation.ResponseBody;
import org.easy4j.framework.bean.Handler;
import org.easy4j.framework.bean.Param;
import org.easy4j.framework.helper.BeanHelper;
import org.easy4j.framework.helper.BeanLoader;
import org.easy4j.framework.helper.ConfigHelper;
import org.easy4j.framework.helper.ControllerHelper;
import org.easy4j.framework.util.JsonUtil;
import org.easy4j.framework.util.ReflectionUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 中央控制器
 */
public class DispatcherServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(DispatcherServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        BeanLoader.init();

        ServletContext servletContext = config.getServletContext();
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping("/index.jsp");
        jspServlet.addMapping(ConfigHelper.getAppViewPath() + "*");

        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping("/favicon.ico");
//        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestMethod = req.getMethod().toLowerCase();
        String requestPath = req.getPathInfo();

        // 根据request获取对应的handler
        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);

        if(handler != null){
            Class<?> controllerClass = handler.getControllerClass();

            Object controllerBean = BeanHelper.getBean(controllerClass);

            Map<String, Object> paramMap = new HashMap();
            Enumeration<String> paramNames = req.getParameterNames();
            if(paramNames != null){
                while(paramNames.hasMoreElements()){
                    String paramName = paramNames.nextElement();
                    String paramValue = req.getParameter(paramName);
                    paramMap.put(paramName, paramValue);
                }
            }


            Param param = new Param(paramMap);
            Method actionMethod = handler.getMethod();
            Object result = ReflectionUtil.invokeMethod(actionMethod, controllerBean, param);
            // 判断method方法上面是否存在 ResponseBody注解,是否返回json数据
            if(actionMethod.isAnnotationPresent(ResponseBody.class)){
                // 返回json数据
                if(result != null){
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    PrintWriter writer = resp.getWriter();
                    String json = JsonUtil.toJson(result);
                    writer.write(json);
                    writer.flush();
                    writer.close();
                }
            }else{
                // 返回页面视图
                if(result != null && result instanceof String){
                    String viewPath = (String)result;
                    if(viewPath.startsWith("/")){
                        resp.sendRedirect(req.getContextPath() + viewPath);
                    }else{
                        req.getRequestDispatcher(ConfigHelper.getAppViewPath() + viewPath).forward(req, resp);
                    }
                }
            }
        }
        LOGGER.info("requestMethod={}, requestPath={}", requestMethod, requestPath);

    }
}
