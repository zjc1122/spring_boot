package com.zjc.designmode.delegate.mvc;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName : DispatcherServlet
 * @Author : zhangjiacheng
 * @Date : 2020/12/19
 * @Description : TODO
 */
public class DispatcherServlet extends HttpServlet {

    private List<Handler> handlers = new ArrayList<>();

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //完成调度
        doDispatch(req,res);
    }

    @Override
    public void init() throws ServletException {
        Class<?> memberControllerClass = MemberController.class;
        try {
            handlers.add(new Handler().setController(memberControllerClass.newInstance())
                    .setMethod(memberControllerClass.getMethod("getMemberById",new Class[]{String.class}))
                    .setUrl("/getMemberById.json"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse res) {
        String uri = req.getRequestURI();
        Handler handler = null;
        for (Handler h : handlers){
            if (uri.equals(h.getUrl())){
                handler = h;
                break;
            }
        }
        try {
            Object obj = handler.getMethod().invoke(handler.getController(), req.getParameter("mid"));
            res.getWriter().write(obj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class Handler{
        private Object controller;
        private Method method;
        private String url;

        public Object getController() {
            return controller;
        }

        public Handler setController(Object controller) {
            this.controller = controller;
            return this;
        }

        public Method getMethod() {
            return method;
        }

        public Handler setMethod(Method method) {
            this.method = method;
            return this;
        }

        public String getUrl() {
            return url;
        }

        public Handler setUrl(String url) {
            this.url = url;
            return this;
        }
    }
//    private void doDispatch(HttpServletRequest req, HttpServletResponse res) throws IOException {
//        String uri = req.getRequestURI();
//        String mid = req.getParameter("mid");
//
//        if ("getMemberById".equals(uri)){
//            new MemberController().getMemberById(mid);
//        }else if ("getOrderById".equals(uri)){
//            new OrderController().getOrderById(mid);
//        }else {
//            res.getWriter().write("404 Not Found!");
//        }
//    }
}
