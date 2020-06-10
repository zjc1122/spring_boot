package cn.zjc.security;

import cn.zjc.util.JsonResult;
import com.google.gson.GsonBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UserLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    public UserLoginSuccessHandler() {
        super();
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setContentType(JsonResult.JSON_CONTENT_TYPE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new GsonBuilder().serializeNulls().create().toJson(JsonResult.success()));
    }
}