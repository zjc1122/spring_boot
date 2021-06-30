package com.zjc.formework.webmvc.servlet;

import com.google.common.collect.Maps;
import com.zjc.spring.mvc.ZRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Map;

/**
 * @ClassName : ZHandlerAdapter
 * @Author : zhangjiacheng
 * @Date : 2021/1/14
 * @Description : TODO
 */
public class ZHandlerAdapter {

    public boolean supports(Object handler){
        return (handler instanceof ZHandlerMapping);
    }

    public ZModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        ZHandlerMapping handlerMapping = (ZHandlerMapping)handler;
        //把方法的形参列表和request的参数列表的顺序一一对应
        Map<String,Integer> paramIndexMapping = Maps.newHashMap();
        Annotation[][] pa = handlerMapping.getMethod().getParameterAnnotations();
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
        Class<?>[] paramsTypes = handlerMapping.getMethod().getParameterTypes();
        //获取方法的形参列表
        for (int i = 0; i < paramsTypes.length; i++) {
            Class<?> type = paramsTypes[i];
            if (type == HttpServletRequest.class || type == HttpServletResponse.class) {
                paramIndexMapping.put(type.getName(),i);
            }
        }


        //获取形参列表
        Map<String,String[]> params = request.getParameterMap();
        //实参列表
        Object[] paramValues = new Object[paramsTypes.length];

        for (Map.Entry<String, String[]> param : params.entrySet()) {
            String value = Arrays.toString(param.getValue()).replaceAll("\\[|\\]", "")
                    .replaceAll("\\s", ",");

            if (!paramIndexMapping.containsKey(param.getKey())){
                continue;
            }
            int index = paramIndexMapping.get(param.getKey());
            paramValues[index] = caseStringValue(value,paramsTypes[index]);
        }

        if (paramIndexMapping.containsKey(HttpServletRequest.class.getName())){
            int reqIndex = paramIndexMapping.get(HttpServletRequest.class.getName());
            paramValues[reqIndex] = request;
        }
        if (paramIndexMapping.containsKey(HttpServletResponse.class.getName())){
            int respIndex = paramIndexMapping.get(HttpServletResponse.class.getName());
            paramValues[respIndex] = response;
        }


        Object result = handlerMapping.getMethod().invoke(handlerMapping.getController(), paramValues);
        if (result == null || result instanceof Void){
            return null;
        }

        boolean isModelAndView = handlerMapping.getMethod().getReturnType() == ZModelAndView.class;
        if (isModelAndView){
            return (ZModelAndView) result;
        }
        return null;
    }

    private Object caseStringValue(String value, Class<?> type) {
        if (String.class==type){
            return value;
        }else if (Integer.class == type) {
            return Integer.valueOf(value);
        }else if (Double.class == type){
            return Double.valueOf(value);
        }else{
            if (value!=null){
                return value;
            }
        }
        return null;
    }
}
