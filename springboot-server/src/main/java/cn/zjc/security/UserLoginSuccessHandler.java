package cn.zjc.security;

import cn.zjc.enums.SystemCodeEnum;
import cn.zjc.util.JsonResult;
import com.google.gson.GsonBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class UserLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    public UserLoginSuccessHandler() {
        super();
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setContentType(JsonResult.JSON_CONTENT_TYPE);
        response.setCharacterEncoding(JsonResult.CHARACTER_ENCODING);
        PrintWriter out = response.getWriter();
        out.write(new GsonBuilder().serializeNulls().create().toJson(JsonResult.success(SystemCodeEnum.LOGIN_SUCCESS.getName())));
        out.flush();
        out.close();
    }
}