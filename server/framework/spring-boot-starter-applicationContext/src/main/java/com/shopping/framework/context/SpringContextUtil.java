package com.shopping.framework.context;

import com.shopping.framework.context.config.ApplicationUtil;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 类描述: applicationContext 操作接口
 *
 * @author GuoFuJun
 * @date 2018/10/25 下午2:20
 */
public class SpringContextUtil {

    /**
     * 通过名字获取上下文中的bean
     *
     * @param name
     * @return
     */
    public static Object getBean(String name) {
        return ApplicationUtil.getApplicationContext().getBean(name);
    }

    /**
     * 通过类型获取上下文中的bean
     *
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> tClass) {
        return ApplicationUtil.getApplicationContext().getBean(tClass);
    }

    /**
     * 通过名字和类型获取上下文中的bean
     *
     * @param name
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name, Class<T> tClass) {
        return ApplicationUtil.getApplicationContext().getBean(name, tClass);
    }

    /**
     * 获得 HttpServletRequest
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return req;
    }

    /**
     * 获得 HttpServletResponse
     *
     * @return
     */
    public static HttpServletResponse getResponse() {
        HttpServletResponse resp = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        return resp;
    }

    /**
     * 获得 HttpSession
     *
     * @return
     */
    public static HttpSession getSession() {
        HttpServletRequest request = getRequest();
        return request.getSession();
    }

    /**
     * 获得 ServletContext
     *
     * @return
     */
    public static ServletContext getServletContext() {
        return ContextLoader.getCurrentWebApplicationContext().getServletContext();
    }

    /**
     * 检测请求的类型
     *
     * @return
     */
    public static boolean isAjax() {
        String requestType = getRequest().getHeader("X-Requested-With");
        if (requestType == null || "".equals(requestType)) {
            return false;
        } else {
            return true;
        }
    }
}
