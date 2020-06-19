package cn.zjc.security;


import cn.zjc.enums.SystemCodeEnum;
import cn.zjc.util.JsonResult;
import com.google.gson.GsonBuilder;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : zhangjiacheng
 * @ClassName : ExpiredSessionService
 * @date : 2018/6/14
 * @Description : session过期策略
 */
@Service
public class ExpiredSessionService implements SessionInformationExpiredStrategy {
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent eventØ) throws IOException {
        HttpServletResponse response = eventØ.getResponse();
        response.setContentType(JsonResult.JSON_CONTENT_TYPE);
        response.setCharacterEncoding(JsonResult.CHARACTER_ENCODING);
        response.getWriter().write(new GsonBuilder().serializeNulls().create().toJson(JsonResult.failed(SystemCodeEnum.EXPIRED_SESSION.getName())));
        response.flushBuffer();
    }
}
