package org.easy4j.framework.core.servlet;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
@WebServlet(urlPatterns = "/*")
public class DispatcherServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(DispatcherServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
       // TODO 初始化操作


    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod().toLowerCase();
        String pathInfo = req.getPathInfo();

        LOGGER.info("method={},path={}", method, pathInfo);

    }
}
