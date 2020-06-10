package cn.zjc.security;

import cn.zjc.util.JsonResult;
import com.google.gson.GsonBuilder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UserLoginFailHandler extends SimpleUrlAuthenticationFailureHandler {

    public UserLoginFailHandler() {
        super();
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException authentication) throws IOException {
        response.setContentType(JsonResult.JSON_CONTENT_TYPE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new GsonBuilder().serializeNulls().create().toJson(JsonResult.failed()));
    }
}