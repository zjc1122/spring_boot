package cn.zjc.handler;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfigurerAdapter extends WebMvcConfigurerAdapter {

    /**
     * 对来自/** 这个链接来的请求进行拦截
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new ErrorInterceptor()).addPathPatterns("/**");

    }

    /**
     * 通过此配置，不用添加LoginController的方法就可以直接通过
     * “http://localhost:8080/login”访问到login.html页面了！
     *
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

}