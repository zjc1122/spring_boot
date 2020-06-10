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
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authHeader = request.getHeader(HEADER_STRING);

        if (authHeader == null || !authHeader.startsWith(TOKEN_PREFIX)){
            chain.doFilter(request, response);
            try {
                response.setContentType(JsonResult.JSON_CONTENT_TYPE);
                response.setCharacterEncoding(JsonResult.CHARACTER_ENCODING);
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                PrintWriter out = response.getWriter();
                out.write(new GsonBuilder().serializeNulls().create().toJson(JsonResult.failed(SystemCodeEnum.EXPIRED_TOKEN.getCode(), SystemCodeEnum.EXPIRED_TOKEN.getName(), SystemCodeEnum.EXPIRED_TOKEN.getInfo())));
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            final String authToken = authHeader.substring(TOKEN_PREFIX.length()).trim();
            Boolean tokenExpired = JwtTokenUtil.isTokenExpired(authToken);
            if (!tokenExpired) {
                throw new IOException(SystemCodeEnum.EXPIRED_TOKEN.getName());
            }
            String username = JwtTokenUtil.getUsernameFromToken(authToken);
            if (StringUtils.isBlank(username)) {
                throw new IOException(SystemCodeEnum.USER_NOT_EXIST.getName());
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
            chain.doFilter(request, response);
        }
    }
}

