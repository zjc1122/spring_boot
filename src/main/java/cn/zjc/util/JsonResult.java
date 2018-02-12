package cn.zjc.util;

import com.google.gson.GsonBuilder;
import lombok.Getter;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Auther zhangjiacheng
 * @Date 2017/8/31 15:23
 */
@Getter
public class JsonResult implements View {

  private static final String JSON_CONTENT_TYPE = "application/json;charset=UTF-8";

  public final static String SUCCESS_CODE = "0000";
  public final static String FAIL_CODE = "1111";
  public final static String SYS_ERROR = "9999";

  public static final JsonResult EMPTY_SUCCESS = success(null);
  public static final JsonResult SYS_REQUEST_ERROR = JsonResult.failed(SYS_ERROR, "系统发生错误,请联系管理员!");

  private final String code;
  private final String Msg;
  private final Object data;

  private JsonResult(String code, String Msg, Object data) {
    this.code = code;
    this.Msg = Msg;
    this.data = data;
  }

  public static JsonResult success(Object data) {
    return new JsonResult(SUCCESS_CODE, null, data);
  }

  public static JsonResult success(Object data,String succeMsg) {
    return new JsonResult(SUCCESS_CODE, null, data);
  }

  public static JsonResult failed(String message) {
    return JsonResult.failed(FAIL_CODE, message, null);
  }

  public static JsonResult failed(String code, String message) {
    return JsonResult.failed(code, message, null);
  }

  public static JsonResult failed(String code, String message, Object data) {
    return new JsonResult(code, message, data);
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
