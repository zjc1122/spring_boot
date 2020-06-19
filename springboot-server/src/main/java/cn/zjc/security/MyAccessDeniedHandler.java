package cn.zjc.security;

import cn.zjc.enums.SystemCodeEnum;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 无访问权限处理类(403)
 */
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN,
                "Authentication Failed: " + SystemCodeEnum.ACCESS_ERROR.getName());
    }

}