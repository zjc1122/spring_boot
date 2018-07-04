//package cn.zjc.oauth2;
//
//import cn.zjc.server.ApplyClientDetailService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
//import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//
//import javax.annotation.Resource;
//
///**
// * @author : zhangjiacheng
// * @ClassName : SecurityConfiguration
// * @date : 2018/7/4
// * @Description : TODO
// */
//@Configuration
//@EnableWebSecurity
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//
//    @Resource
//    private RedisConnectionFactory redisConnection;
//
//    public ApplyClientDetailService clientDetails() {
//        return new ApplyClientDetailService();
//    }
//
//    @Bean
//    @Override
//    protected UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withUsername("user_1").password("123456").authorities("USER").build());
//        manager.createUser(User.withUsername("user_2").password("123456").authorities("USER").build());
//        return manager;
//    }
//
//    @Bean
//    public TokenStore tokenStore() {
//        return new RedisTokenStore(redisConnection);
//    }
//
//    @Bean
//    @Autowired
//    public TokenStoreUserApprovalHandler userApprovalHandler(TokenStore tokenStore) {
//        TokenStoreUserApprovalHandler handler = new TokenStoreUserApprovalHandler();
//        handler.setTokenStore(tokenStore());
//        handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetails()));
//        handler.setClientDetailsService(clientDetails());
//        return handler;
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .requestMatchers().anyRequest()
//                .and().authorizeRequests().antMatchers("/oauth/*").permitAll();
//    }
//}