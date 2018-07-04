//package cn.zjc.oauth2;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//
//import javax.annotation.Resource;
//
//@Configuration
//@EnableResourceServer
//public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
//
//    private Logger logger = LoggerFactory.getLogger(ResourceServerConfiguration.class);
//
//    @Resource
//    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint ;
//
//    @Bean
//    public CustomLogoutSuccessHandler customLogoutSuccessHandler(){
//        return new CustomLogoutSuccessHandler();
//    }
//    private static final String DEMO_RESOURCE_ID = "*";
//
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) {
//        resources.resourceId(DEMO_RESOURCE_ID).stateless(true);
//    }
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                .and().requestMatchers().anyRequest()
//                .and().anonymous()
//                .and().authorizeRequests()
////                    .antMatchers("/product/**").access("#oauth2.hasScope('select') and hasRole('ROLE_USER')")
//                .antMatchers("/**").authenticated();  //配置访问权限控制，必须认证过后才可以访问
//    }
////    @Override
////    public void configure(HttpSecurity http) throws Exception {
////        logger.info("=========================111111111=========");
////       http.exceptionHandling()
////               .authenticationEntryPoint(customAuthenticationEntryPoint)
////               .and()
////               .logout()
////               .logoutUrl("/oauth/logout")
////               .logoutSuccessHandler(customLogoutSuccessHandler())
////               .and()
////               .authorizeRequests()
////               .antMatchers("/hello/").permitAll()
////               .antMatchers("/secure/**").authenticated();
////    }
//}