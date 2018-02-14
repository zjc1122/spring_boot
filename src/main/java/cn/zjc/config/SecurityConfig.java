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
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.security.web.session.SimpleRedirectSessionInformationExpiredStrategy;
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
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/login").permitAll()
            .antMatchers("/register").permitAll()
            .anyRequest().authenticated()
            .and().sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry).expiredUrl("/login").and()
            .and().logout().invalidateHttpSession(Boolean.TRUE).clearAuthentication(Boolean.TRUE).permitAll()
            .and().httpBasic();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    /**
     * 生成x-auth-token信息，如果生成SESSION则注释掉即可
     * @return
     */
    @Bean
    public HttpSessionStrategy httpSessionStrategy() {
        return new HeaderHttpSessionStrategy();
    }

    /**
     * session失效跳转
     */
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
        return new SimpleRedirectSessionInformationExpiredStrategy("/login");
    }
    /**
     * SpringSecurity内置的session监听器
     */
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}