package cn.zjc.config;

import cn.zjc.server.SysUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.session.ConcurrentSessionFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.security.web.session.SimpleRedirectSessionInformationExpiredStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 2 * 60)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private SessionRegistry sessionRegistry;

    private  static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Bean
    UserDetailsService sysUserService() {
        return new SysUserDetailsService();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(sysUserService()).passwordEncoder(new PasswordEncoder() {
            @Override
            public String encode(CharSequence raw) {
                return encoder.encode(raw);
            }

            @Override
            public boolean matches(CharSequence raw, String s) {
                if(!encoder.matches(raw, s)){
                    throw new BadCredentialsException("密码错误！");
                }
                return encoder.matches(raw, s);
            }
        });
        //不删除凭证
        auth.eraseCredentials(false);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(myValidCodeProcessingFilter(), UsernamePasswordAuthenticationFilter.class);
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/login").permitAll()
            .antMatchers("/register").permitAll()
            .anyRequest().authenticated()
            .and().sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry).expiredUrl("/login").and()
            .and().logout().logoutUrl("/login333").invalidateHttpSession(Boolean.TRUE).clearAuthentication(Boolean.TRUE).permitAll()
            .and().httpBasic();
    }

    /**
     * session失效跳转
     */
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
        return new SimpleRedirectSessionInformationExpiredStrategy("/login");
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

    @Bean
    public MyValidCodeProcessingFilter myValidCodeProcessingFilter() throws Exception {
        MyValidCodeProcessingFilter filter = new MyValidCodeProcessingFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
//        //设置登陆成功后跳转的URL
//        filter.setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler("/index.html"));
//        //设置登陆失败后跳转的URL
//        //如果不设置这两个属性，会导致登陆成功后访问默认地址/
//        filter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler("/login.html?error=1"));
        filter.setSessionAuthenticationStrategy(new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry));
        return filter;
    }
}