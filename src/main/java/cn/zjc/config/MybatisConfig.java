package cn.zjc.config;

import cn.zjc.aspect.db.DynamicDataSource;
import cn.zjc.enums.DataBaseTypeEnum;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;

/**
 * @author : zhangjiacheng
 * @ClassName : MybatisConfig
 * @date : 2018/6/11
 * @Description : mybatis配置类
 */
@Configuration
@MapperScan("cn.zjc.mapper")
public class MybatisConfig implements TransactionManagementConfigurer {
    @Resource
    DataSource defaultDataSource;
    @Resource
    DataSource slaveDataSource;
    private static final Logger logger = LoggerFactory.getLogger(MybatisConfig.class);

    @Bean
    @Primary
    public DynamicDataSource dataSource() {
        try {
            Map<Object, Object> targetDataSources = Maps.newHashMap();
            targetDataSources.put(DataBaseTypeEnum.Default_DB.get(), defaultDataSource);
            targetDataSources.put(DataBaseTypeEnum.Slave_DB.get(), slaveDataSource);
            DynamicDataSource dataSource = new DynamicDataSource();
            // 该方法是AbstractRoutingDataSource的方法
            dataSource.setTargetDataSources(targetDataSources);
            // 默认的datasource设置为defaultDataSource
            dataSource.setDefaultTargetDataSource(defaultDataSource);
            return dataSource;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 事务管理,具体使用在service层加入@Transactional注解
     */
    @Bean(name = "transactionManager")
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean(DynamicDataSource ds) {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(ds);
        bean.setTypeAliasesPackage("cn.zjc.model");
        //分页插件,插件无非是设置mybatis的拦截器
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);
        //添加插件
        bean.setPlugins(new Interceptor[]{pageHelper});
        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            //设置xml扫描路径
            bean.setMapperLocations(resolver.getResources("classpath:mapping/*.xml"));
            return bean.getObject();
        } catch (Exception e) {
            throw new RuntimeException("sqlSessionFactory init fail", e);
        }
    }
}
