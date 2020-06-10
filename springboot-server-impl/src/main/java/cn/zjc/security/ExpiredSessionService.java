package cn.zjc.security;


import cn.zjc.enums.SystemCodeEnum;
import cn.zjc.util.JsonResult;
import com.google.gson.GsonBuilder;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author : zhangjiacheng
 * @ClassName : ExpiredSessionService
 * @date : 2018/6/14
 * @Description : 登录被踢掉时返回信息
 */
@Service
public class ExpiredSessionService implements SessionInformationExpiredStrategy {
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent eventØ) throws IOException {
        eventØ.getResponse().setContentType(JsonResult.JSON_CONTENT_TYPE);
        eventØ.getResponse().setCharacterEncoding(JsonResult.CHARACTER_ENCODING);
        eventØ.getResponse().getWriter().write(new GsonBuilder().serializeNulls().create().toJson(JsonResult.failed(SystemCodeEnum.EXPIRED_SESSION.getName())));
    }
}
