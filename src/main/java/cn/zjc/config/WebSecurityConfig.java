package cn.zjc.config;

import cn.zjc.enums.SysUtilCode;
import cn.zjc.server.util.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;

/**
 * @author : zhangjiacheng
 * @ClassName : WebSecurityConfig
 * @date : 2018/6/13
 * @Description :
 * @EnableWebSecurity: 禁用Boot的默认Security配置，配合@Configuration启用自定义配置（需要扩展WebSecurityConfigurerAdapter）
 * @EnableGlobalMethodSecurity(prePostEnabled = true): 启用Security注解，例如最常用的@PreAuthorize
 * configure(HttpSecurity): Request层面的配置，对应XML Configuration中的<http>元素
 * configure(WebSecurity): Web层面的配置，一般用来配置无需安全检查的路径
 * configure(AuthenticationManagerBuilder): 身份验证配置，用于注入自定义身份验证Bean和密码校验规则
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 5 * 60)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Bean
    UserDetailsService sysUserService() {
        return new MyUserDetailsService();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(sysUserService()).passwordEncoder(new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return encoder.encode(rawPassword);
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                if (!encoder.matches(rawPassword, encodedPassword)) {
                    throw new BadCredentialsException(SysUtilCode.PASSWORD_ERROR.getDesc());
                }
                return encoder.matches(rawPassword, encodedPassword);
            }
        });
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login", "/register", "/transport/*", "/test/**").permitAll()
                .anyRequest().authenticated()
                .and().logout().invalidateHttpSession(Boolean.TRUE).clearAuthentication(Boolean.TRUE).permitAll()
                .and().httpBasic();
        http
                .sessionManagement()
                .maximumSessions(1)
                //false之后登录踢掉之前登录,true则不允许之后登录
                .maxSessionsPreventsLogin(false)
                //登录被踢掉时的自定义操作(session失效)
                .expiredSessionStrategy(sessionInformationExpiredStrategy)
                .sessionRegistry(sessionRegistry())
                .expiredUrl("/login");
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/js/**", "/css/**", "/images/**", "/**/favicon.ico");
    }

    /**
     * 生成x-auth-token头信息,格式为(x-auth-token : 062e995b-6e21-4ad5-a96c-b837389d5f7e)
     * 注释掉此方法则生成session头信息，格式为(Cookie : SESSION=09cc4ffe-7ad5-4455-962b-148053effb34)
     * 在postman用2个账号登录获得的session是一样的
     */
    @Bean
    public HttpSessionStrategy httpSessionStrategy() {
        return new HeaderHttpSessionStrategy();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    /**
     * SpringSecurity内置的session监听器
     */
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    /**
     * 允许跨域
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*")
                        .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowCredentials(false).maxAge(3600);
            }
        };
    }
}