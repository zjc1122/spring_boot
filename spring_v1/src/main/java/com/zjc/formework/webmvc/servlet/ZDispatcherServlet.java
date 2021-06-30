package com.zjc.formework.webmvc.servlet;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zjc.formework.context.ZApplicationContext;
import com.zjc.spring.mvc.ZController;
import com.zjc.spring.mvc.ZRequestMapping;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName : ZDispatcherServlet
 * @Author : zhangjiacheng
 * @Date : 2021/1/13
 * @Description : TODO
 */
public class ZDispatcherServlet extends HttpServlet {

    private final String CONTEXT_CONFIG_LOCATION = "contextConfigLocation";

    private ZApplicationContext context;

    private List<ZHandlerMapping> handlerMappings = Lists.newArrayList();

    private Map<ZHandlerMapping,ZHandlerAdapter> handlerAdapters = Maps.newHashMap();

    private List<ZViewResolver> viewResolvers = Lists.newArrayList();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            this.doDispatcher(req,resp);
        }catch (Exception e){
            e.printStackTrace();
            try {
                processDispatchResult(req,resp,new ZModelAndView("500"));
            } catch (Exception ex) {
                ex.printStackTrace();
                resp.getWriter().write("500 Exception,Detail : " + Arrays.toString(e.getStackTrace()));
            }
//            resp.getWriter().write("500 Exception,Detail : " + Arrays.toString(e.getStackTrace()));
        }

    }

    private void doDispatcher(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        //1.通过从request中拿到url，去匹配一个handlerMapping
        ZHandlerMapping handler = getHandler(req);
        if (Objects.isNull(handler)){
            //返回404
            processDispatchResult(req,resp,new ZModelAndView("404"));
            return;
        }
        //2.准备好调用前的参数
        ZHandlerAdapter ha = getHandlerAdapter(handler);

        //3.调用方法,返回modelAndView存储了要传页面的值和页面模板的名称
        ZModelAndView mv = ha.handle(req, resp, handler);
        //输出
        processDispatchResult(req, resp, mv);
    }

    private void processDispatchResult(HttpServletRequest req, HttpServletResponse resp, ZModelAndView mv) throws Exception {
        //把ModelAndView变成一个html、json等
        if (Objects.isNull(mv)) {
            return;
        }
        //
        if (this.viewResolvers.isEmpty()){
            return;
        }
        for (ZViewResolver viewResolver : this.viewResolvers) {
            ZView view = viewResolver.resolveViewName(mv.getViewName(), null);
            view.render(mv.getModel(),req,resp);
            return;
        }

    }

    private ZHandlerAdapter getHandlerAdapter(ZHandlerMapping handler) {
        if (this.handlerAdapters.isEmpty()){
            return null;
        }
        ZHandlerAdapter ha = this.handlerAdapters.get(handler);
        if (ha.supports(handler)){
            return ha;
        }
        return null;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        //1.初始化ApplicationContext
        context = new ZApplicationContext(config.getInitParameter(CONTEXT_CONFIG_LOCATION));
        //2.初始化springmvc的九大组件
        initStrategies(context);
//        System.out.println(context);
    }

    protected void initStrategies(ZApplicationContext context) {
        //多文件上传组件
        initMultipartResolver(context);
        //初始化本地语言环境
        initLocaleResolver(context);
        //初始化模板处理器
        initThemeResolver(context);
        //handlerMapping
        initHandlerMappings(context);
        //初始化参数适配器
        initHandlerAdapters(context);
        //初始化异常拦截器
        initHandlerExceptionResolvers(context);
        //初始化视图预处理器
        initRequestToViewNameTranslator(context);
        //初始化图形转换器
        initViewResolvers(context);
        //初始化缓存组件,参数缓存器
        initFlashMapManager(context);
    }

    private void initFlashMapManager(ZApplicationContext context) {

    }

    private void initViewResolvers(ZApplicationContext context) {
        //获得模板的存放目录
        String templateRoot = context.getConfig().getProperty("templateRoot");
        String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();
        File templateRootDir = new File(templateRootPath);
        String[] templates = templateRootDir.list();
        for (int i = 0;i<templates.length;i++) {
            //兼容多模板
            this.viewResolvers.add(new ZViewResolver(templateRoot));
        }

    }

    private void initRequestToViewNameTranslator(ZApplicationContext context) {

    }

    private void initHandlerExceptionResolvers(ZApplicationContext context) {
    }

    private void initHandlerAdapters(ZApplicationContext context) {

        //把一个request请求变成一个handler，参数都是字符串，自动匹配到handler中的形参，需要依赖于handlerMapping，有几个handlerMapping，就有几个HandlerAdapters

        for (ZHandlerMapping handlerMapping : handlerMappings) {
            this.handlerAdapters.put(handlerMapping,new ZHandlerAdapter());
        }

    }

    private void initHandlerMappings(ZApplicationContext context) {
        String[] beanNames = context.getBeanDefinitionNames();

        for (String beanName : beanNames) {
            Object controller = context.getBean(beanName);
            Class<?> clazz = controller.getClass();
            if (!clazz.isAnnotationPresent(ZController.class)){
                continue;
            }

            //保存写在类上面的
            String baseUrl = "";
            if (clazz.isAnnotationPresent(ZRequestMapping.class)) {
                ZRequestMapping annotation = clazz.getAnnotation(ZRequestMapping.class);
                baseUrl = annotation.value();
            }

            //默认获取所有public方法
            for (Method method : clazz.getMethods()) {
                if (!method.isAnnotationPresent(ZRequestMapping.class)) {
                    continue;
                }
                ZRequestMapping requestMapping = method.getAnnotation(ZRequestMapping.class);

                String regex = ("/" + baseUrl + "/" + requestMapping.value().replaceAll("\\*",".*")).replaceAll("/+", "/");
                Pattern pattern = Pattern.compile(regex);
                this.handlerMappings.add(new ZHandlerMapping(pattern, controller, method));
                System.out.println("mapped:" + regex + "," + method);
            }
        }

    }

    private void initThemeResolver(ZApplicationContext context) {

    }

    private void initLocaleResolver(ZApplicationContext context) {

    }

    private void initMultipartResolver(ZApplicationContext context) {

    }

    private ZHandlerMapping getHandler(HttpServletRequest req) {
        if (this.handlerMappings.isEmpty()){
            return null;
        }
        //绝对路径
        String url = req.getRequestURI();
        //相对路径
        String contextPath = req.getContextPath();
        url = url.replaceAll(contextPath, "").replaceAll("/+", "/");

        for (ZHandlerMapping handler : this.handlerMappings) {
            Matcher matcher = handler.getPattern().matcher(url);
            if (!matcher.matches()){
                continue;
            }
            return handler;
        }
        return null;
    }
}
