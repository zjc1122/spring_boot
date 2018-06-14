package cn.zjc.server;


import cn.zjc.enums.SysUtilCode;
import cn.zjc.util.JsonResult;
import com.google.gson.GsonBuilder;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import java.io.IOException;

/**
 * @author : zhangjiacheng
 * @ClassName : ExpiredSessionService
 * @date : 2018/6/14
 * @Description : 登录被踢掉时返回信息
 */
public class ExpiredSessionService implements SessionInformationExpiredStrategy {
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent eventØ) throws IOException {
        eventØ.getResponse().setContentType(JsonResult.JSON_CONTENT_TYPE);
        eventØ.getResponse().setCharacterEncoding("UTF-8");
        eventØ.getResponse().getWriter().write(new GsonBuilder().serializeNulls().create().toJson(JsonResult.failed(SysUtilCode.EXPIRED_SESSION.getDesc())));
    }
}
