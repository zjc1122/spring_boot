package com.zjc.formework.webmvc.servlet;

import java.util.Map;

/**
 * @ClassName : ZModleAndView
 * @Author : zhangjiacheng
 * @Date : 2021/1/14
 * @Description : TODO
 */
public class ZModelAndView {

    private String viewName;

    private Map<String,?> model;

    public ZModelAndView(String viewName) {
        this.viewName = viewName;
    }

    public ZModelAndView(String viewName, Map<String, ?> model) {
        this.viewName = viewName;
        this.model = model;
    }

    public String getViewName() {
        return viewName;
    }

    public Map<String, ?> getModel() {
        return model;
    }
}
