package cn.zjc.designmode.strategy;

import com.google.common.collect.Maps;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author : zhangjiacheng
 * @ClassName : ModelConfig
 * @Date : 2019/12/23
 * @Description : TODO
 */
@Configuration
public class ModelConfig {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public Map<Integer, ModelService> modelMap() {
        Map<Integer, ModelService> map = Maps.newHashMap();
        Model1ServiceImpl model1 = applicationContext.getBean(Model1ServiceImpl.class);
        Model2ServiceImpl model2 = applicationContext.getBean(Model2ServiceImpl.class);
        Model3ServiceImpl model3 = applicationContext.getBean(Model3ServiceImpl.class);
        map.put(1, model1);
        map.put(2, model2);
        map.put(3, model3);
        return map;
    }
}
