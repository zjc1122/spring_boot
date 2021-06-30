package com.zjc.formework.context;

import com.google.common.collect.Maps;
import com.zjc.formework.aop.ZAopProxy;
import com.zjc.formework.aop.ZCglibAopProxy;
import com.zjc.formework.aop.ZJdkDynamicAopProxy;
import com.zjc.formework.aop.config.ZAopConfig;
import com.zjc.formework.aop.support.ZAdvisedSupport;
import com.zjc.formework.beans.ZBeanFactory;
import com.zjc.formework.beans.ZBeanWrapper;
import com.zjc.formework.beans.config.ZBeanDefinition;
import com.zjc.formework.beans.config.ZBeanPostProcessor;
import com.zjc.formework.beans.support.ZBeanDefinitionReader;
import com.zjc.formework.beans.support.ZDefaultListableBeanFactory;
import com.zjc.spring.mvc.ZAutowired;
import com.zjc.spring.mvc.ZController;
import com.zjc.spring.mvc.ZService;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @ClassName : ZApplicationContext
 * @Author : zhangjiacheng
 * @Date : 2021/1/6
 * @Description : TODO
 */
public class ZApplicationContext extends ZDefaultListableBeanFactory implements ZBeanFactory{

    private String[] locations;

    private ZBeanDefinitionReader reader;

    //单例的IOC容器
    private Map<String,Object> singletonObjects = Maps.newConcurrentMap();

    private Map<String,ZBeanWrapper> factoryBeanInstanceCache = Maps.newConcurrentMap();

    public ZApplicationContext(String... locations){
        this.locations = locations;
        refresh();
    }


    @Override
    protected void refresh() {
        //1.定位，定位配置文件
        reader = new ZBeanDefinitionReader(this.locations);
        //2.加载配置文件，扫描相关的类,封装成ZBeanDefinition
        List<ZBeanDefinition> beanDefinitions = reader.loadBeanDefinitions();
        //3.注册，把注册信息放到容器里面
        doRegisterBeanDefinition(beanDefinitions);
        //4.非延时加载的类提前初始化好
        doAutowire();
    }

    //只处理非延时加载的情况
    private void doAutowire() {
        for (Map.Entry<String, ZBeanDefinition> beanDefinitionEntry : super.beanDefinitionMap.entrySet()) {
            String beanName = beanDefinitionEntry.getKey();
            if (!beanDefinitionEntry.getValue().isLazyInit()){
                getBean(beanName);
            }
        }
    }

    private void doRegisterBeanDefinition(List<ZBeanDefinition> beanDefinitions) {
        beanDefinitions.forEach(beanDefinition -> super.beanDefinitionMap.put(beanDefinition.getFactoryBeanName(),beanDefinition));
    }
    @Override
    public Object getBean(Class<?> beanClass){
        return getBean(beanClass.getName());
    }

    @Override
    public Object getBean(String beanName) {
        ZBeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);

        //1、初始化
        Object instance = instantiateBean(beanName, beanDefinition);

        ZBeanPostProcessor postProcessor = new ZBeanPostProcessor();

        try {
            postProcessor.postProcessBeforeInitialization(instance,beanName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //把这个对象封装到ZBeanWrapper中
        ZBeanWrapper beanWrapper = new ZBeanWrapper(instance);


        this.factoryBeanInstanceCache.put(beanName,beanWrapper);
        try {
            postProcessor.postProcessAfterInitialization(instance,beanName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //2、注入
        populateBean(beanName,new ZBeanDefinition(),beanWrapper);

        return this.factoryBeanInstanceCache.get(beanName).getWrappedInstance();
    }

    private void populateBean(String beanName, ZBeanDefinition beanDefinition, ZBeanWrapper zBeanWrapper) {
        Object instance = zBeanWrapper.getWrappedInstance();

        Class<?> clazz = zBeanWrapper.getWrappedClass();
        if (!clazz.isAnnotationPresent(ZController.class) && !clazz.isAnnotationPresent(ZService.class)){
            return;
        }

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (!field.isAnnotationPresent(ZAutowired.class)){
                continue;
            }
            ZAutowired autowired = field.getAnnotation(ZAutowired.class);
            String autowiredBeanName = autowired.value().trim();
            if ("".equals(autowiredBeanName)){
                autowiredBeanName = field.getType().getName();
            }

            field.setAccessible(true);

            try {
                if (this.factoryBeanInstanceCache.get(autowiredBeanName)==null){
                    continue;
                }
                field.set(instance,this.factoryBeanInstanceCache.get(autowiredBeanName).getWrappedInstance());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private Object instantiateBean(String beanName, ZBeanDefinition beanDefinition) {

        //1.拿到实例化对象的类名
        String className = beanDefinition.getBeanClassName();
        //2.反射实例化，获得一个对象
        Object instance = null;
        try {
            if (this.singletonObjects.containsKey(className)){
                instance = this.singletonObjects.get(className);
            }else {
                Class<?> clazz = Class.forName(className);
                instance = clazz.newInstance();
                ZAdvisedSupport config = instantionAopConfig(beanDefinition);
                config.setTargetClass(clazz);
                config.setTarget(instance);
                //符合pointCut的规则，创建代理对象
                if (config.pointCutMatch()){
                    instance = createProxy(config).getProxy();
                }
                this.singletonObjects.put(className,instance);
                this.singletonObjects.put(beanDefinition.getFactoryBeanName(),instance);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        //4.把ZBeanWrapper对象存放到IOC容器中
        return instance;
    }

    private ZAopProxy createProxy(ZAdvisedSupport config) {

        Class<?> targetClass = config.getTargetClass();
        if (targetClass.getInterfaces().length > 0){
            return new ZJdkDynamicAopProxy(config);
        }
        return new ZCglibAopProxy(config);
    }

    private ZAdvisedSupport instantionAopConfig(ZBeanDefinition beanDefinition) {

        ZAopConfig config = new ZAopConfig();
        config.setPointCut(this.reader.getConfig().getProperty("pointCut"));
        config.setAspectClass(this.reader.getConfig().getProperty("aspectClass"));
        config.setAspectBefore(this.reader.getConfig().getProperty("aspectBefore"));
        config.setAspectAfter(this.reader.getConfig().getProperty("aspectAfter"));
        config.setAspectAfterThrow(this.reader.getConfig().getProperty("aspectAfterThrow"));
        config.setAspectAfterThrowingName(this.reader.getConfig().getProperty("aspectAfterThrowingName"));
        config.setAspectAround(this.reader.getConfig().getProperty("aspectAround"));
        return new ZAdvisedSupport(config);
    }

    public String[] getBeanDefinitionNames(){
        return this.beanDefinitionMap.keySet().toArray(new String[this.beanDefinitionMap.size()]);
    }

    public int getBeanDefinitionCount(){
        return this.beanDefinitionMap.size();
    }

    public Properties getConfig(){
        return this.reader.getConfig();
    }
}
