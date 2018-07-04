//package cn.zjc.oauth2;
//
//import cn.zjc.oauth2.ApplyClientDetailService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.bind.RelaxedPropertyResolver;
//import org.springframework.context.EnvironmentAware;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
//import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
//import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
//
//import javax.annotation.Resource;
//import javax.sql.DataSource;
//
///**
// * @author : zhangjiacheng
// * @ClassName : AuthorizationServerConfiguration
// * @date : 2018/7/4
// * @Description : 配置OAuth2验证服务器
// */
//@Configuration
//@EnableAuthorizationServer
//public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter implements EnvironmentAware {
//
//    private static final Logger logger = LoggerFactory.getLogger(AuthorizationServerConfiguration.class);
//
//    private static final String ENV_OAUTH = "authentication.oauth.";
//    private static final String PROP_CLIENT_ID = "clientId";
//    private static final String PROP_SECRET = "secret";
//    private static final String PROP_TOKEN_VALIDITY_SECONDS = "tokenValidityInSeconds";
//
//    private RelaxedPropertyResolver propertyResolver;
//
//    @Resource
//    RedisConnectionFactory redisConnectionFactory;
//    @Autowired
//    private DataSource dataSource;
//    @Resource
//    private AuthenticationManager authenticationManager;
//
//    /**
//     * 初始化JdbcTokenStore
//     * 基于JDBC的实现，令牌（Access Token）会保存到数据库
//     *
//     * @return
//     */
//    @Bean
//    public TokenStore getTokenStore() {
//        return new JdbcTokenStore(dataSource);
//    }
//
//    /**
//     * 初始化RedisTokenStore
//     * 基于Redis的实现，令牌（Access Token）会保存到Redis
//     *
//     * @return
//     */
//    @Bean
//    public TokenStore getRedisTokenStore() {
//        return new RedisTokenStore(redisConnectionFactory);
//    }
//
//    /**
//     * 使用内存中的 token store
//     *
//     * @return
//     */
//    @Bean
//    public TokenStore getMemoryTokenStore() {
//        return new InMemoryTokenStore();
//    }
//
//    public ApplyClientDetailService getClientDetails() {
//        return new ApplyClientDetailService();
//    }
//
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
//        endpoints.tokenStore(getRedisTokenStore())
//                .authenticationManager(authenticationManager);
//    }
//
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        // 配置客户端, 用于client认证
//        clients.withClientDetails(getClientDetails());
//        //使用存在内存中配置
////        clients.inMemory()
////                //client_id用来标识客户的Id
////                .withClient(propertyResolver.getProperty(PROP_CLIENT_ID))
////                //允许授权范围
////                .scopes("read", "write")
////                //客户端可以使用的权限
////                .authorities("ROLE_ADMIN", "ROLE_USER")
////                //允许授权类型
////                .authorizedGrantTypes("password", "refresh_token")
////                //secret客户端安全码
////                .secret(propertyResolver.getProperty(PROP_SECRET))
////                .accessTokenValiditySeconds(propertyResolver.getProperty(PROP_TOKEN_VALIDITY_SECONDS, Integer.class, 1800));
//    }
//
//    @Override
//    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
//        //允许表单认证
//        oauthServer.allowFormAuthenticationForClients();
//    }
//
//    @Override
//    public void setEnvironment(Environment environment) {
//        //获取到前缀是"authentication.oauth." 的属性列表值.
//        this.propertyResolver = new RelaxedPropertyResolver(environment, ENV_OAUTH);
//    }
//}