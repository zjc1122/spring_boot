package com.zjc.config;

import com.zjc.server.IUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private static final String SIGNING_KEY = "zjc";

    /**
     * 数据库连接池对象
     */
    @Resource
    private DataSource dataSource;

    /**
     * 认证业务对象(UserDetailsService)
     */
    @Resource
    private IUserService userService;

    /**
     * 授权模式专用对象
     */
    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 客户端信息来源
     * @return
     */
    @Bean
    public JdbcClientDetailsService jdbcClientDetailsService(){
        JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(dataSource);
        jdbcClientDetailsService.setPasswordEncoder(bCryptPasswordEncoder);
        return jdbcClientDetailsService;
    }

    /**
     * token保存策略(数据库方式)
     * @return
     */
    @Bean
    public TokenStore jdbcTokenStore(){
        return new JdbcTokenStore(dataSource);
    }

    /**
     * token保存策略(JWT)
     * @return
     */
    @Bean
    public TokenStore jwtTokenStore(){
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter(){
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(SIGNING_KEY);
        return converter;
    }

    /**
     * 授权信息保存策略
     * @return
     */
    @Bean
    public ApprovalStore approvalStore(){
        return new JdbcApprovalStore(dataSource);
    }

    /**
     * 授权码模式数据来源
     * @return
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices(){
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    @Bean
    public AuthorizationServerTokenServices tokenServices(){
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        //token存储策略
        tokenServices.setTokenStore(jdbcTokenStore());
//        tokenServices.setTokenStore(jwtTokenStore());
//        //token增强(配合jwt模式)
//        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
//        tokenEnhancerChain.setTokenEnhancers(Collections.singletonList(accessTokenConverter()));
//        tokenServices.setTokenEnhancer(tokenEnhancerChain);
        //是否产生刷新令牌
        tokenServices.setSupportRefreshToken(true);
        //客户端信息
        tokenServices.setClientDetailsService(jdbcClientDetailsService());
        // token有效期30天
        tokenServices.setAccessTokenValiditySeconds( (int) TimeUnit.DAYS.toSeconds(30));
        tokenServices.setRefreshTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30));
        return tokenServices;
    }

    /**
     * 指定客户端信息的数据库来源
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(jdbcClientDetailsService());
    }

    /**
     * token的安全策略
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients();
        security.checkTokenAccess("permitAll()");
        security.tokenKeyAccess("permitAll()");
    }

    /**
     * OAuth2的主配置信息
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .userDetailsService(userService)
                .approvalStore(approvalStore())
                //密码模式需要
                .authenticationManager(authenticationManager)
                //授权码模式需要
                .authorizationCodeServices(authorizationCodeServices())
                //允许post提交，默认配置就是post
                .allowedTokenEndpointRequestMethods(HttpMethod.POST)
                //配置令牌管理
                .tokenServices(tokenServices());

        // 替换授权页面的url
//        endpoints.pathMapping("/oauth/confirm_access","/custom/confirm_access");


    }

    public static void main(String[] args) {
        String zjc = BCrypt.hashpw("zjc", BCrypt.gensalt());
        System.out.println(zjc);
    }

}
