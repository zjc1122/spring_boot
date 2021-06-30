package com.zjc.formework.webmvc.servlet;

import lombok.Data;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * @ClassName : ZHandlerMapping
 * @Author : zhangjiacheng
 * @Date : 2021/1/13
 * @Description : TODO
 */
public class ZHandlerMapping {

    private Object controller;
    private Method method;
    //url的正则匹配
    private Pattern pattern;

    public ZHandlerMapping(Pattern pattern, Object controller, Method method) {
        this.controller = controller;
        this.method = method;
        this.pattern = pattern;
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }
}
