package cn.zjc.test;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhangjiacheng on 2017/12/10
 * http://blog.csdn.net/linzhiqiang0316/article/details/52600839
 */
public class test implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String url = httpServletRequest.getRequestURL().toString();
        String method = httpServletRequest.getMethod();
        String uri = httpServletRequest.getRequestURI();
        String queryString = httpServletRequest.getQueryString();
        System.out.println(httpServletRequest.getParameterMap());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println(111111);
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println(2222222);
    }
}
