package cn.zjc.security;

import cn.zjc.enums.SystemCodeEnum;
import cn.zjc.util.JsonResult;
import cn.zjc.util.JwtTokenUtil;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 验证token
 */
@Component
public class JwtVerifyFilter extends BasicAuthenticationFilter {

    /** Token前缀 **/
    public static final String TOKEN_PREFIX = "Bearer";
    /** 存放Token的Header Key **/
    public static final String HEADER_STRING = "Authorization";

    private UserDetailsService userDetailsService;

    public JwtVerifyFilter(AuthenticationManager authenticationManager,UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
    }


    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authHeader = request.getHeader(HEADER_STRING);
        if (StringUtils.isNotBlank(authHeader) && authHeader.startsWith(TOKEN_PREFIX)) {
            String authToken = authHeader.substring(TOKEN_PREFIX.length()).trim();
            boolean tokenExpired = JwtTokenUtil.isTokenExpired(authToken);
            if (!tokenExpired) {
                response.setContentType(JsonResult.JSON_CONTENT_TYPE);
                response.setCharacterEncoding(JsonResult.CHARACTER_ENCODING);
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                PrintWriter out = response.getWriter();
                out.write(new GsonBuilder().serializeNulls().create().toJson(JsonResult.failed(SystemCodeEnum.EXPIRED_TOKEN.getCode(), SystemCodeEnum.EXPIRED_TOKEN.getName(), SystemCodeEnum.EXPIRED_TOKEN.getInfo())));
                out.flush();
                out.close();
                return;
            }
            String username = JwtTokenUtil.getUsernameFromToken(authToken);
            if (StringUtils.isBlank(username)) {
                response.setContentType(JsonResult.JSON_CONTENT_TYPE);
                response.setCharacterEncoding(JsonResult.CHARACTER_ENCODING);
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                PrintWriter out = response.getWriter();
                out.write(new GsonBuilder().serializeNulls().create().toJson(JsonResult.failed(SystemCodeEnum.USER_NOT_EXIST.getCode(), SystemCodeEnum.USER_NOT_EXIST.getName(), SystemCodeEnum.USER_NOT_EXIST.getInfo())));
                out.flush();
                out.close();
                return;
            }
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (JwtTokenUtil.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                            request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        chain.doFilter(request, response);
    }
}

