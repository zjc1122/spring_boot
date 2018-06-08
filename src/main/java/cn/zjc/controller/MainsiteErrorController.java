package cn.zjc.controller;

import cn.zjc.model.SystemMsg;
import cn.zjc.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Controller
public class MainsiteErrorController implements ErrorController {

    @Autowired
    private ErrorAttributes errorAttributes;

    private static final String ERROR_PATH = "/error";

    @RequestMapping(value=ERROR_PATH)
    @ResponseBody
    public JsonResult handleError(HttpServletRequest request, HttpServletResponse response) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

        Map<String,Object> errorAttributes = getErrorAttributes(request, true);
        String message=(String)errorAttributes.get("message");
        String error = (String) errorAttributes.get("error");
        Integer status=(Integer)errorAttributes.get("status");
        String path=(String)errorAttributes.get("path");
        Date timestamp = (Date) errorAttributes.get("timestamp");

        SystemMsg systemMsg = SystemMsg.builder().message(message).status(status).error(error).path(path).sysTime(sdf.format(timestamp)).build();

        String msg;

        switch (response.getStatus()){

            case 401:
                msg = "访问未授权!";
                break;
            case 404:
                msg = "找不到页面!";
                break;
            case 403:
                msg = "访问被拒绝!";
                break;
            case 500:
                msg = "服务器错误!";
                break;
            default:
                msg= "系统繁忙";
        }
        return JsonResult.failed(JsonResult.SYS_ERROR,msg,systemMsg);
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
    }
}