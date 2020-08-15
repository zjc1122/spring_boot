package cn.zjc.security;

import cn.zjc.enums.SystemCodeEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
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
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60 * 60)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] AUTH_WHITELIST = {
            "/logout/success",
            "/oauth/**",
            "/login",
            "/logout",
            "/register",
            "/transport/*",
            "/test/**"
    };

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Resource
    private UserDetailsService userDetailsService;

    /**
     * 生成x-auth-token头信息,格式为(x-auth-token : 062e995b-6e21-4ad5-a96c-b837389d5f7e)
     * 注释掉此方法则生成session头信息，格式为(Cookie : SESSION=09cc4ffe-7ad5-4455-962b-148053effb34)
     * 在postman用2个账号登录获得的session是一样的
     * 1.3版本
     */
//    @Bean
//    public HttpSessionStrategy httpSessionStrategy() {
//        return new HeaderHttpSessionStrategy();
//    }

    /**
     * 2.2版本
     * @return
     */
    @Bean
    public HeaderHttpSessionIdResolver httpSessionStrategy() {
        return new HeaderHttpSessionIdResolver("x-auth-token");
    }

    /**
     * session注册
     * @return
     */
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
     * AuthenticationManager对象在OAuth2认证服务中要使用，提前放入IOC容器中
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
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

    @Bean
    public LogoutSuccessHandler getLogoutSuccessHandler(){
        return new UserLogoutSuccessHandler();
    }

    @Bean
    public AuthenticationSuccessHandler getLoginSuccessHandler(){
        return new UserLoginSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler getLoginFailHandler(){return new UserLoginFailHandler();}

    /**
     * 注册自定义的UsernamePasswordAuthenticationFilter
     */
    @Bean
    public UsernamePasswordAuthenticationFilter myAuthenticationFilter() throws Exception {
        MyUsernamePasswordAuthenticationFilter filter = new MyUsernamePasswordAuthenticationFilter();
        //使用自定义的登录成功返回结果
        filter.setAuthenticationSuccessHandler(getLoginSuccessHandler());
        filter.setAuthenticationFailureHandler(getLoginFailHandler());
        filter.setFilterProcessesUrl("/login");
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }
    /**
     * 身份认证失败处理类
     * @return AuthenticationEntryPoint
     */
    @Bean
    public AuthenticationEntryPoint myLoginUrlAuthenticationEntryPoint() {
        return new MyLoginUrlAuthenticationEntryPoint();
    }

    @Bean
    public AccessDeniedHandler myAccessDeniedHandler() {
        return new MyAccessDeniedHandler();
    }

    @Bean
    public SessionInformationExpiredStrategy expiredSessionService() {
        return new ExpiredSessionService();
    }

    /**
     * 认证用户
     * @param auth
     * @throws Exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return bCryptPasswordEncoder.encode(rawPassword);
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                if (!bCryptPasswordEncoder.matches(rawPassword, encodedPassword)) {
                    throw new BadCredentialsException(SystemCodeEnum.PASSWORD_ERROR.getName());
                }
                return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
            }
        });
    }

    /**
     * 生成session格式的登录方式
     * @throws Exception
     */
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//
//        http
//                //关闭csrf
//                .csrf().disable()
//                //开放test路径
//                .authorizeRequests().antMatchers(AUTH_WHITELIST).
//                permitAll()
//                .anyRequest().authenticated()
//                //开启自动配置的登陆功能
//                .and()
//                //自定义登录请求路径(post请求)
//                .formLogin().usernameParameter("username").passwordParameter("password")
//                .and()
//                //关闭拦截未登录自动跳转,改为返回json信息
//                .exceptionHandling().authenticationEntryPoint(myLoginUrlAuthenticationEntryPoint()).accessDeniedHandler(myAccessDeniedHandler())
//                //Session管理
//                .and()
//                .sessionManagement()
//                //设置允许的session最大个数,-1为无数个
//                .maximumSessions(-1)
//                .expiredSessionStrategy(expiredSessionService())
//                .maxSessionsPreventsLogin(false)
//                .sessionRegistry(sessionRegistry())
//                .and()
//                //开启自动配置的注销功能
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                //session无效
//                .invalidateHttpSession(true)
//                //清空cook的值
//                .deleteCookies("x-auth-token")
//                //指定登出成功后的处理，如果指定了这个，那么logoutSuccessUrl就不会生效。
//                .logoutSuccessHandler(getLogoutSuccessHandler()).permitAll()
//                .and()
//                //用重写的Filter替换掉原有的UsernamePasswordAuthenticationFilter
//                .addFilterAt(myAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//    }

    /**
     * 生成jwt的token登录方式
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated()
                .and()
                //关闭Session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtVerifyFilter(super.authenticationManager(),userDetailsService))
                .addFilter(new JwtAuthenticationFilter(super.authenticationManager()));
//        http
//                .logout().logoutUrl("/logout").logoutSuccessHandler(getLogoutSuccessHandler()).permitAll();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/js/**", "/css/**", "/images/**");
    }

}