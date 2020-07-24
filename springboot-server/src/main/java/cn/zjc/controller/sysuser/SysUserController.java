package cn.zjc.controller.sysuser;

import cn.zjc.enums.SystemCodeEnum;
import cn.zjc.model.sysuser.SysUser;
import cn.zjc.server.sysuser.SysUserService;
import cn.zjc.util.GsonUtil;
import cn.zjc.util.HttpClientUtil;
import cn.zjc.util.JsonResult;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

/**
 * @author : zhangjiacheng
 * @ClassName : SysUserController
 * @date : 2018/6/15
 * @Description : 系统用户Controller
 */
@RestController
@Slf4j
public class SysUserController {

    private static final String URL = "http://localhost:9002/oauth/token";

    @Resource
    private SysUserService sysUserService;

    /**
     * 注册接口
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public JsonResult register(@RequestParam("username") String username, @RequestParam("password") String password) {
        SysUser user = sysUserService.getByUsername(username);
        if (user != null) {
            return JsonResult.failed("用户名已存在");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(16);
        SysUser sysUser = new SysUser();
        sysUser.setLoginName(username);
        sysUser.setUsername(username);
        sysUser.setPassword(passwordEncoder.encode(password));
        sysUser.setSalt(UUID.randomUUID().toString());
        sysUserService.save(sysUser);
        return JsonResult.success("用户名:" + sysUser.getUsername() + "注册成功!");
    }

    /**
     * 注销成功返回结果
     * @return
     */
    @RequestMapping(value = "/logout/success", method = RequestMethod.GET)
    public JsonResult logoutSuccess() {
        return JsonResult.success(SystemCodeEnum.LOGOUT_SUCCESS.getName());
    }

    /**
     * 根据oauth的code换取token,并将token返回给前端
     * @return
     */
    @RequestMapping(value = "/oauth/callback", method = RequestMethod.GET)
    public JsonResult oauthToken(@RequestParam("code") String code) {
        log.info("code--->{}",code);
        if (StringUtils.isBlank(code)){
            return JsonResult.failed("code为空");
        }

        ConcurrentMap<String, String> map = Maps.newConcurrentMap();
        map.put("grant_type","authorization_code");
        map.put("code",code);
        map.put("client_id","zjc");
        map.put("client_secret","123");
        Response response;
        try {
            response = HttpClientUtil.doPost(URL, map);
        } catch (IOException e) {
            e.printStackTrace();
            return JsonResult.failed(e.getMessage());
        }
        if (response.code()!= HttpServletResponse.SC_OK){
            return JsonResult.failed("获取token失败");
        }
        String token = null;
        try {

            String body = Objects.requireNonNull(response.body()).string();
            Gson gson = GsonUtil.getGson();
            JsonObject jsonObject = gson.fromJson(body, JsonObject.class);
            token = jsonObject.get("access_token").getAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("token--->{}",token);
        return JsonResult.success(token);
    }

    /**
     * 刷新token
     * @return
     */
    @RequestMapping(value = "/oauth/refresh", method = RequestMethod.POST)
    public JsonResult refreshToken() throws IOException {

        ConcurrentMap<String, String> map = Maps.newConcurrentMap();
        map.put("grant_type","refresh_token");
        map.put("refresh_token","c5979107-3d94-4d4a-98e1-dba7926be8bf");
        map.put("client_id","zjc");
        map.put("client_secret","123");
        Response response;
        try {
            response = HttpClientUtil.doPost(URL, map);
        } catch (IOException e) {
            e.printStackTrace();
            return JsonResult.failed(e.getMessage());
        }
        if (response.code()!= HttpServletResponse.SC_OK){
            log.info(Objects.requireNonNull(response.body()).string());
            return JsonResult.failed("刷新token失败");
        }
        String token = null;
        try {

            String body = Objects.requireNonNull(response.body()).string();
            Gson gson = GsonUtil.getGson();
            JsonObject jsonObject = gson.fromJson(body, JsonObject.class);
            token = jsonObject.get("access_token").getAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("token--->{}",token);
        return JsonResult.success(token);
    }

}
