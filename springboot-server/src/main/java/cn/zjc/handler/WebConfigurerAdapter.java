package cn.zjc.handler;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebConfigurerAdapter extends WebMvcConfigurationSupport {

    /**
     * 自定义请求进行拦截
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry
                //拦截器实例
                .addInterceptor(new MyInterceptor())
                //拦截的路径
                .addPathPatterns("/**")
                //不拦截的路径
                .excludePathPatterns("/aaa/bbb/ccc/ddd");

    }

}