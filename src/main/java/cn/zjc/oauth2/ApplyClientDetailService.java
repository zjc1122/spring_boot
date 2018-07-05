//package cn.zjc.oauth2;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.oauth2.provider.ClientDetails;
//import org.springframework.security.oauth2.provider.ClientDetailsService;
//import org.springframework.security.oauth2.provider.ClientRegistrationException;
//import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
//import org.springframework.stereotype.Service;
//
//import javax.sql.DataSource;
//
//@Service
//public class ApplyClientDetailService implements ClientDetailsService {
//
//    @Autowired
//    private DataSource dataSource;
//
//    @Override
//    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
//        JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(dataSource);
//        ClientDetails clientDetails = jdbcClientDetailsService.loadClientByClientId(clientId);
//        return clientDetails;
//    }
//}