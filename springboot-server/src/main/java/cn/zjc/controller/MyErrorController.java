package cn.zjc.controller;

import cn.zjc.enums.SystemCodeEnum;
import cn.zjc.model.util.SystemMsg;
import cn.zjc.util.JsonResult;
import cn.zjc.util.TimeUtil;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * @Author : zhangjiacheng
 * @ClassName : MyErrorController
 * @Date : 2018/6/10
 * @Description : 统一异常Controller
 */
@Controller
public class MyErrorController implements ErrorController {

    @Resource
    private ErrorAttributes errorAttributes;

    private static final String ERROR_PATH = "/error";

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request) {
        //1.5.8版本使用
//        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        //2.3.1版本使用
        ServletWebRequest servletWebRequest = new ServletWebRequest(request);
        return errorAttributes.getErrorAttributes(servletWebRequest, true);
    }

    @RequestMapping(value = ERROR_PATH)
    @ResponseBody
    public JsonResult handleError(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> errorAttributes = getErrorAttributes(request);
        String message = (String) errorAttributes.get("message");
        String error = (String) errorAttributes.get("error");
        Integer status = (Integer) errorAttributes.get("status");
        String path = (String) errorAttributes.get("path");
        Date timestamp = (Date) errorAttributes.get("timestamp");
        SystemMsg systemMsg = SystemMsg
                .builder()
                .message(message)
                .status(status)
                .error(error)
                .path(path)
                .sysTime(TimeUtil.formatTime(TimeUtil.dateToLocalDateTime(timestamp)))
                .build();
        String msg;

        switch (response.getStatus()) {

            case 400:
                msg = "参数错误";
                break;
            case 401:
                msg = "访问未授权";
                break;
            case 404:
                msg = "找不到页面";
                break;
            case 403:
                msg = "访问无权限";
                break;
            case 500:
                msg = "服务器错误";
                break;
            case 405:
                msg = "请求方式错误";
                break;
            default:
                msg = "系统繁忙";
        }
        return JsonResult.failed(SystemCodeEnum.ERROR.getCode(), msg, systemMsg);
    }
}