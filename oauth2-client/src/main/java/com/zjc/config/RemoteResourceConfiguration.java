package com.zjc.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * @ClassName : RemoteResourceConfiguration
 * @Author : zhangjiacheng
 * @Date : 2020/7/26
 * @Description : TODO
 */
@Configuration
@EnableOAuth2Client
public class RemoteResourceConfiguration {

    @Bean
    public OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails(){
        return new AuthorizationCodeResourceDetails();
    }

    @Bean
    public OAuth2RestTemplate restOperations(@Qualifier("oauth2ClientContext") OAuth2ClientContext context) {
        OAuth2RestTemplate template = new OAuth2RestTemplate(oAuth2ProtectedResourceDetails(), context);

        AuthorizationCodeAccessTokenProvider authCodeProvider = new AuthorizationCodeAccessTokenProvider();
        authCodeProvider.setStateMandatory(false);
        AccessTokenProviderChain provider = new AccessTokenProviderChain(
                Collections.singletonList(authCodeProvider));
        template.setAccessTokenProvider(provider);
        return template;
    }

    @Bean
    public RemoteTokenServices tokenService(/*OAuth2ProtectedResourceDetails details*/) {
        RemoteTokenServices tokenService = new RemoteTokenServices();
        tokenService.setCheckTokenEndpointUrl("http://localhost:9002/oauth/check_token");
        tokenService.setClientId("zjc");
        tokenService.setClientSecret("123");
        return tokenService;
    }

    /**
     * 注册处理redirect uri的filter
     * @param oauth2RestTemplate
     * @param tokenService
     * @return
     */
    @Bean
    public OAuth2ClientAuthenticationProcessingFilter oauth2ClientAuthenticationProcessingFilter(
            OAuth2RestTemplate oauth2RestTemplate,
            RemoteTokenServices tokenService) {
        OAuth2ClientAuthenticationProcessingFilter filter
                = new OAuth2ClientAuthenticationProcessingFilter("http://localhost:9002/oauth/token");
        filter.setRestTemplate(oauth2RestTemplate);
        filter.setTokenServices(tokenService);


        //设置回调成功的页面
        filter.setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                this.setDefaultTargetUrl("/home");
                super.onAuthenticationSuccess(request, response, authentication);
            }
        });
        return filter;
    }

//    /**
//     * 注册check token服务
//     * @param details
//     * @return
//     */
//    @Bean
//    public RemoteTokenServices tokenService(OAuth2ProtectedResourceDetails details) {
//        RemoteTokenServices tokenService = new RemoteTokenServices();
//        tokenService.setCheckTokenEndpointUrl("http://localhost:9002/oauth/check_token");
//        System.out.println(details.getClientId());
//        tokenService.setClientId(details.getClientId());
//        tokenService.setClientSecret(details.getClientSecret());
//        return tokenService;
//    }
}
