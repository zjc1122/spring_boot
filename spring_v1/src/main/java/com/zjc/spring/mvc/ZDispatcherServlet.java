package com.zjc.spring.mvc;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * @ClassName : ZJCDispatcherServlet
 * @Author : zhangjiacheng
 * @Date : 2020/12/28
 * @Description : TODO
 */
public class ZDispatcherServlet extends HttpServlet {

    //保存application.properties配置文件中的内容
    private Properties contextConfig = new Properties();
    //保存扫描到的所有类名
    private List<String> classNames = new ArrayList<>();

    private Map<String, Object> ioc = new HashMap<>();
    //保存url和method对应关系
//    private Map<String, Method> handlerMapping = new HashMap<>();
    private List<HandlerMapping> handlerMapping = new ArrayList();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //6.调用，运行
        try {
            doDispatch(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("500 Exception,Detail : " + Arrays.toString(e.getStackTrace()));
        }


    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        HandlerMapping handlerMapping = getHandler(req);

        if (handlerMapping == null) {
            resp.getWriter().write("404 Not Found ");
            return;
        }
        //获取形参列表
        Class<?>[] paramTypes = handlerMapping.getParamTypes();

        Object[] paramValues = new Object[paramTypes.length];

        Map<String,String[]> params = req.getParameterMap();

        for (Map.Entry<String, String[]> param : params.entrySet()) {
            String value = Arrays.toString(param.getValue()).replaceAll("\\[|\\]", "")
                    .replaceAll("\\s", ",");

            if (!handlerMapping.paramIndexMapping.containsKey(param.getKey())){
                continue;
            }
            int index = handlerMapping.paramIndexMapping.get(param.getKey());
            paramValues[index] = convert(paramTypes[index], value);
        }

        if (handlerMapping.paramIndexMapping.containsKey(HttpServletRequest.class.getName())){
            int reqIndex = handlerMapping.paramIndexMapping.get(HttpServletRequest.class.getName());
            paramValues[reqIndex] = req;
        }
        if (handlerMapping.paramIndexMapping.containsKey(HttpServletResponse.class.getName())){
            int respIndex = handlerMapping.paramIndexMapping.get(HttpServletResponse.class.getName());
            paramValues[respIndex] = resp;
        }


        Object invoke = handlerMapping.method.invoke(handlerMapping.controller, paramValues);
        if (invoke == null || invoke instanceof Void){
            return;
        }
        resp.getWriter().write(invoke.toString());
    }

    private HandlerMapping getHandler(HttpServletRequest req) {
        if (handlerMapping.isEmpty()){
            return null;
        }
        //绝对路径
        String url = req.getRequestURI();
        //相对路径
        String contextPath = req.getContextPath();
        url = url.replaceAll(contextPath, "").replaceAll("/+", "/");
        for (HandlerMapping mapping : this.handlerMapping) {
            if (mapping.getUrl().equals(url)){
                return mapping;
            }
        }
        return null;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        //1.加载配置文件
        doLoadConfig(config.getInitParameter("contextConfigLocation"));
        //2.扫描相关的类
        doScanner(contextConfig.getProperty("scanPackage"));
        //3.初始化扫描到的类，并将它们放到IOC容器中
        doInstance();
        //4.完成依赖注入
        doAutowired();
        //5.初始化HandlerMapping
        initHandlerMapping();
        System.out.println("初始化完成");

    }

    //初始化url和Method的关联关系
    private void initHandlerMapping() {
        if (ioc.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Class<?> clazz = entry.getValue().getClass();
            if (!clazz.isAnnotationPresent(ZController.class)) {
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
                ZRequestMapping annotation = method.getAnnotation(ZRequestMapping.class);
                String url = (baseUrl + "/" + annotation.value()).replaceAll("/+", "/");

//                handlerMapping.put(url, method);
                this.handlerMapping.add(new HandlerMapping(url, method, entry.getValue()));
                System.out.println("mapped:" + url + "," + method);
            }


        }

    }

    //自动注入
    private void doAutowired() {
        if (ioc.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Field[] declaredFields = entry.getValue().getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                if (!field.isAnnotationPresent(ZAutowired.class)) {
                    continue;
                }
                ZAutowired annotation = field.getAnnotation(ZAutowired.class);
                String beanName = annotation.value().trim();

                if ("".equals(beanName)) {
                    beanName = field.getType().getName();

                    field.setAccessible(true);
                    try {
                        //反射动态给字段赋值
                        field.set(entry.getValue(), ioc.get(beanName));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

    }

    private void doInstance() {
        if (classNames.isEmpty()) {
            return;
        }
        try {
            for (String className : classNames) {
                Class<?> clazz = Class.forName(className);
                if (clazz.isAnnotationPresent(ZController.class)) {
                    Object o = clazz.newInstance();
                    String beanName = toLowerFirstCase(clazz.getSimpleName());
                    ioc.put(beanName, o);
                } else if (clazz.isAnnotationPresent(ZService.class)) {
                    ZService annotation = clazz.getAnnotation(ZService.class);
                    String beanName = annotation.value();
                    if ("".equals(beanName.trim())) {
                        beanName = toLowerFirstCase(clazz.getSimpleName());
                    }
                    Object o = clazz.newInstance();
                    ioc.put(beanName, o);
                    for (Class<?> i : clazz.getInterfaces()) {
                        if (ioc.containsKey(i.getName())) {
                            throw new Exception("bean" + i.getName() + "is exists");
                        }
                        //接口的类型当key
                        ioc.put(i.getName(), o);
                    }
                } else {
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 扫描出相关的类
     *
     * @param scanPackage
     */
    private void doScanner(String scanPackage) {
        //包路径转换为文件路径
        URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.", "/"));
        File classpath = new File(url.getFile());
        for (File file : classpath.listFiles()) {
            if (file.isDirectory()) {
                doScanner(scanPackage + "." + file.getName());
            } else {
                if (!file.getName().endsWith(".class")) {
                    continue;
                }
                String className = (scanPackage + "." + file.getName().replace(".class", ""));
                classNames.add(className);
            }
        }
    }

    /**
     * 加载配置文件
     *
     * @param contextConfigLocation
     */
    private void doLoadConfig(String contextConfigLocation) {
        //直接从类路径下找到spring主配置文件所在的路径，将其读取出来放到properties中。
        //相当于scanPackage=com.zjc.mvc从文件中保存到内存中
        InputStream fis = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);
        try {
            contextConfig.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(fis)) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String toLowerFirstCase(String simpleName) {
        char[] chars = simpleName.toCharArray();
        //大小写字母的ASCII码差32
        chars[0] += 32;
        return String.valueOf(chars);
    }

    /**
     * url传过来的是String类型，需要把String转换为任意类型
     * 需要改造成策略模式去匹配其他类型
     *
     * @param type
     * @param value
     * @return
     */
    private Object convert(Class<?> type, String value) {
        if (Integer.class == type) {
            return Integer.valueOf(value);
        }
        return value;
    }

    /**
     * 保存url和method的关系
     */
    private static class HandlerMapping{

        private String url;
        private Method method;
        private Object controller;
        private Class<?> [] paramTypes;


        public String getUrl() {
            return url;
        }

        public Method getMethod() {
            return method;
        }

        public Object getController() {
            return controller;
        }

        public Class<?>[] getParamTypes() {
            return paramTypes;
        }

        //形参列表，参数名字作为key，位置作为value
        private Map<String,Integer> paramIndexMapping;

        public HandlerMapping(String url, Method method, Object controller) {
            this.url = url;
            this.method = method;
            this.controller = controller;
            paramIndexMapping = new HashMap<>();
            putParamIndexMapping(method);
            paramTypes = method.getParameterTypes();
        }

        private void putParamIndexMapping(Method method) {
            //拿到方法上的注解，得到二维数组
            //一个参数有多个注解，一个方法有多个参数
            Annotation[][] pa = method.getParameterAnnotations();
            for (int i = 0; i < pa.length; i++) {
                for (Annotation a : pa[i]) {
                    //只解析@ZRequestParam
                    if (a instanceof ZRequestParam) {
                        //得到参数的名称去http://localhost:8080/spring_v1/demo/query?name=zjc匹配
                        String paramName = ((ZRequestParam) a).value();
                        if (!"".equals(paramName.trim())){
                            paramIndexMapping.put(paramName,i);
                        }
                    }
                }
            }
            //提取request和response
            Class<?>[] parameterTypes = method.getParameterTypes();
            //获取方法的形参列表
            for (int i = 0; i < parameterTypes.length; i++) {
                Class<?> type = parameterTypes[i];
                if (type == HttpServletRequest.class || type == HttpServletResponse.class) {
                    paramIndexMapping.put(type.getName(),i);
                }
            }

        }
    }
}
