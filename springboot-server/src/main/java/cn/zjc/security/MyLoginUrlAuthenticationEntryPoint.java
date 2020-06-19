package cn.zjc.security;

import cn.zjc.enums.SystemCodeEnum;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName : MyLoginUrlAuthenticationEntryPoint
 * @Author : zhangjiacheng
 * @Date : 2020/6/19
 * @Description : 身份认证失败处理类(401)
 */
public class MyLoginUrlAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                "Authentication Failed: " + SystemCodeEnum.EXPIRED_SESSION.getName());
    }
}
