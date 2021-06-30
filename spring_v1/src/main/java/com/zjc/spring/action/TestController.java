package com.zjc.spring.action;

import com.google.common.collect.Maps;
import com.zjc.formework.webmvc.servlet.ZModelAndView;
import com.zjc.spring.mvc.ZAutowired;
import com.zjc.spring.mvc.ZController;
import com.zjc.spring.mvc.ZRequestMapping;
import com.zjc.spring.mvc.ZRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * @ClassName : TestController
 * @Author : zhangjiacheng
 * @Date : 2020/12/29
 * @Description : TODO
 */
@ZController
@ZRequestMapping("/demo")
public class TestController {

    @ZAutowired("demoService")
    private IDemoService demoService;

    @ZRequestMapping("/query.json")
    public ZModelAndView query(HttpServletRequest req, HttpServletResponse resp, @ZRequestParam("name") String name){

        String result = null;
        try {
            result = demoService.get(name);
        } catch (Exception e) {
            Map<String, Object> model = Maps.newHashMap();
            model.put("detail",e.getCause().getMessage());
            model.put("stackTrace",e.getCause().getStackTrace());
            return new ZModelAndView("500",model);
        }
        return out(resp,result);
//        return result;
    }

    private ZModelAndView out(HttpServletResponse resp, String result) {
        try {
            resp.getWriter().write(new Date() + "\r\n");
            resp.getWriter().write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @ZRequestMapping("/add.json")
    public void add(HttpServletRequest req, HttpServletResponse resp,
                    @ZRequestParam("a") Integer a, @ZRequestParam("b") Integer b){

        try {
            resp.getWriter().write(new Date() + "\r\n");
            resp.getWriter().write(a + "+" + b + "=" + (a+b));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ZRequestMapping("/remove")
    public ZModelAndView remove(HttpServletResponse resp, @ZRequestParam("id") Integer id, @ZRequestParam("name")String name){
        String result = null;
        try {
            result = demoService.remove(id,name);

        } catch (Exception e) {
            Map<String, Object> model = Maps.newHashMap();
            model.put("detail",e.getCause().getMessage());
            model.put("stackTrace", Arrays.toString(e.getStackTrace()).replaceAll("\\[|\\]",""));
            return new ZModelAndView("500",model);
        }
        return out(resp,result);
    }
}
