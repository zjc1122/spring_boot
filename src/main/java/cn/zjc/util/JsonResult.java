package cn.zjc.util;

import cn.zjc.enums.SystemCodeEnum;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author : zhangjiacheng
 * @ClassName : JsonResult
 * @Date : 2018/6/10
 * @Description : 返回结果
 */
@Getter
public class JsonResult implements View {


    public static final String JSON_CONTENT_TYPE = "application/json;charset=UTF-8";
    public static final JsonResult EMPTY_SUCCESS = JsonResult.success(null);
    public static final JsonResult SYS_REQUEST_ERROR = JsonResult.failed(SystemCodeEnum.ERROR.getCode(), SystemCodeEnum.ERROR.getDesc(), "系统发生错误");

    private final Integer code;
    private final String msg;
    private final Object data;

    private JsonResult(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static JsonResult success(Object data) {
        return new JsonResult(SystemCodeEnum.SUCCESS.getCode(), SystemCodeEnum.SUCCESS.getDesc(), data);
    }

    public static JsonResult success(String msg, Object data) {
        return new JsonResult(SystemCodeEnum.SUCCESS.getCode(), msg, data);
    }

    public static JsonResult success(Integer code, String msg, Object data) {
        return new JsonResult(code, msg, data);
    }

    public static JsonResult failed(Object data) {
        return new JsonResult(SystemCodeEnum.ERROR.getCode(), SystemCodeEnum.ERROR.getDesc(), data);
    }

    public static JsonResult failed(String msg, Object data) {
        return JsonResult.failed(SystemCodeEnum.ERROR.getCode(), msg, data);
    }

    public static JsonResult failed(Integer code, String msg, Object data) {
        return new JsonResult(code, msg, data);
    }

    @Override
    public String getContentType() {
        return JSON_CONTENT_TYPE;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(JSON_CONTENT_TYPE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new GsonBuilder().serializeNulls().create().toJson(this));
    }
}
