package cn.zjc.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.sql.SQLException;

/**
 * @author : zhangjiacheng
 * @ClassName : DataSourcesConfig
 * @date : 2018/6/11
 * @Description : 数据源配置类
 */
@Configuration
public class DataSourcesConfig {
    @Value("${data.datasource.url}")
    private String url;
    @Value("${data.datasource.username}")
    private String username;
    @Value("${data.datasource.password}")
    private String password;
    @Value("${data.datasource.driverClassName}")
    private String driverClassName;
    @Value("${data.druid.maxActive}")
    private Integer maxActive;
    @Value("${data.druid.minIdle}")
    private Integer minIdle;
    @Value("${data.druid.initialSize}")
    private Integer initialSize;
    @Value("${data.druid.maxWait}")
    private Long maxWait;
    @Value("${data.druid.timeBetweenEvictionRunsMillis}")
    private Long timeBetweenEvictionRunsMillis;
    @Value("${data.druid.minEvictableIdleTimeMillis}")
    private Long minEvictableIdleTimeMillis;
    @Value("${data.druid.maxPoolPreparedStatementPerConnectionSize}")
    private Integer maxPoolPreparedStatementPerConnectionSize;
    @Value("${data.druid.validationQuery}")
    private String validationQuery;
    @Value("${data.druid.testWhileIdle}")
    private Boolean testWhileIdle;
    @Value("${data.druid.testOnBorrow}")
    private Boolean testOnBorrow;
    @Value("${data.druid.testOnReturn}")
    private Boolean testOnReturn;
    @Value("${data.druid.poolPreparedStatements}")
    private Boolean poolPreparedStatements;
    @Value("${data.druid.filters}")
    private String filters;

    @Resource
    private Environment env;

    /**
     * druid初始化
     *
     * @return
     * @throws SQLException
     */
    @Bean(name = "defaultDataSource", destroyMethod = "close")
    public DruidDataSource defaultDataSource() throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);
        //配置最大连接
        dataSource.setMaxActive(maxActive);
        //配置初始连接
        dataSource.setInitialSize(initialSize);
        //配置最小连接
        dataSource.setMinIdle(minIdle);
        //连接等待超时时间
        dataSource.setMaxWait(maxWait);
        //间隔多久进行检测,关闭空闲连接
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        //一个连接最小生存时间
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        //用来检测是否有效的sql
        dataSource.setValidationQuery(validationQuery);
        dataSource.setTestWhileIdle(testWhileIdle);
        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setTestOnReturn(testOnReturn);
        //打开PSCache,并指定每个连接的PSCache大小
        dataSource.setPoolPreparedStatements(poolPreparedStatements);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        //配置sql监控的filter
        dataSource.setFilters(filters);
        try {
            dataSource.init();
        } catch (SQLException e) {
            throw new RuntimeException("druid datasource init fail");
        }
        return dataSource;
    }

    @Bean(name = "slaveDataSource", destroyMethod = "close")
    public DruidDataSource slaveDataSource() throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(env.getProperty("data.datasource.url"));
        dataSource.setUsername(env.getProperty("data.datasource.username"));
        dataSource.setPassword(env.getProperty("data.datasource.password"));
        dataSource.setDriverClassName(driverClassName);
        //配置最大连接
        dataSource.setMaxActive(maxActive);
        //配置初始连接
        dataSource.setInitialSize(initialSize);
        //配置最小连接
        dataSource.setMinIdle(minIdle);
        //连接等待超时时间
        dataSource.setMaxWait(maxWait);
        //间隔多久进行检测,关闭空闲连接
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        //一个连接最小生存时间
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        //用来检测是否有效的sql
        dataSource.setValidationQuery(validationQuery);
        dataSource.setTestWhileIdle(testWhileIdle);
        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setTestOnReturn(testOnReturn);
        //打开PSCache,并指定每个连接的PSCache大小
        dataSource.setPoolPreparedStatements(poolPreparedStatements);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        //配置sql监控的filter
        dataSource.setFilters(filters);
        try {
            dataSource.init();
        } catch (SQLException e) {
            throw new RuntimeException("druid datasource init fail");
        }
        return dataSource;
    }

    /**
     * druid监控
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean reg = new ServletRegistrationBean();
        reg.setServlet(new StatViewServlet());
        reg.addUrlMappings("/druid/*");
        //reg.addInitParameter("allow", "127.0.0.1");
        //reg.addInitParameter("deny","");
        reg.addInitParameter("loginUsername", "admin");
        reg.addInitParameter("loginPassword", "123456");
        return reg;
    }

    /**
     * druid监控过滤
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
}
