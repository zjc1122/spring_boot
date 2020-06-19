package cn.zjc.security;

import cn.zjc.enums.SystemCodeEnum;
import cn.zjc.util.JsonResult;
import com.google.gson.GsonBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 注销成功
 */
@Component
public class UserLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {

    public UserLogoutSuccessHandler() {
        super();
    }

    /**
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onLogoutSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException, ServletException {
        if (ObjectUtils.isEmpty(authentication)){
            response.setContentType(JsonResult.JSON_CONTENT_TYPE);
            response.setCharacterEncoding(JsonResult.CHARACTER_ENCODING);
            PrintWriter out = response.getWriter();
            out.write(new GsonBuilder().serializeNulls().create().toJson(JsonResult.success(SystemCodeEnum.LOGOUT_ALREADY.getName())));
            out.flush();
            out.close();
            return;
        }
        String redirectUrl = response.encodeRedirectURL("/logout/success");
        if (logger.isDebugEnabled()) {
            logger.debug("Redirecting to '" + redirectUrl + "'");
        }
        response.sendRedirect(redirectUrl);
    }

}