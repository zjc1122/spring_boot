package cn.zjc.security;

import cn.zjc.enums.SystemCodeEnum;
import cn.zjc.util.JsonResult;
import cn.zjc.util.JwtTokenUtil;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 认证生成token
 */
@Component
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    /** Token前缀 **/
    public static final String TOKEN_PREFIX = "Bearer ";
    /** 存放Token的Header Key **/
    public static final String HEADER_STRING = "Authorization";

    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                    username, password);
            return authenticationManager.authenticate(authRequest);
        } catch (Exception e) {
            try {
                response.setContentType(JsonResult.JSON_CONTENT_TYPE);
                response.setCharacterEncoding(JsonResult.CHARACTER_ENCODING);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                PrintWriter out = response.getWriter();
                out.write(new GsonBuilder().serializeNulls().create().toJson(JsonResult.failed(SystemCodeEnum.USER_NOT_EXIST.getCode(), SystemCodeEnum.USER_NOT_EXIST.getName(), SystemCodeEnum.USER_NOT_EXIST.getInfo())));
                out.flush();
                out.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        String token = JwtTokenUtil.generateToken(authResult.getName());
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        try {
            response.setContentType(JsonResult.JSON_CONTENT_TYPE);
            response.setCharacterEncoding(JsonResult.CHARACTER_ENCODING);
            response.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = response.getWriter();
            out.write(new GsonBuilder().serializeNulls().create().toJson(JsonResult.success()));
            out.flush();
            out.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}

