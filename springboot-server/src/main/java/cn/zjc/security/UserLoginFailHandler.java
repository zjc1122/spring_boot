package cn.zjc.security;

import cn.zjc.enums.SystemCodeEnum;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败
 */
@Component
public class UserLoginFailHandler extends SimpleUrlAuthenticationFailureHandler {

    public UserLoginFailHandler() {
        super();
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException authentication) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                "Authentication Failed: " + SystemCodeEnum.VERIFY_ERROR.getName());
    }
}