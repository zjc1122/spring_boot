package com.zjc.formework.beans.support;

import com.google.common.collect.Lists;
import com.zjc.formework.beans.config.ZBeanDefinition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * @ClassName : ZBeanDefinitionReader
 * @Author : zhangjiacheng
 * @Date : 2021/1/7
 * @Description : TODO
 */
public class ZBeanDefinitionReader {

    private List<String> registyBeanClasses = Lists.newArrayList();
    private Properties config = new Properties();

    //固定配置文件中的key，相当于xml的规范
    private final String SCAN_PACKAGE = "scanPackage";

    public ZBeanDefinitionReader(String... locations){
        //通过url定位找到其所对应的文件，然后转换为文件流
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(locations[0].replace("classpath:",""));
        try {
            config.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (null!=is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        doScanner(config.getProperty(SCAN_PACKAGE));
    }

    private void doScanner(String scanPackage) {
        //包路径转换为文件路径
//        URL url = this.getClass().getResource("/" + scanPackage.replaceAll("\\.", "/"));
        URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.", "/"));
        File classpath = new File(url.getFile());
        for (File file : classpath.listFiles()) {
            if (file.isDirectory()) {
                doScanner(scanPackage + "." + file.getName());
            } else {
                if (!file.getName().endsWith(".class")) {
                    continue;
                }
                String className = (scanPackage + "." + file.getName().replace(".class", ""));
                registyBeanClasses.add(className);
            }
        }
    }

    public Properties getConfig(){
        return this.config;
    }
    //把配置文件中扫描到的所有配置信息转换为ZBeanDefinition对象，以便于IOC操作方便
    public List<ZBeanDefinition> loadBeanDefinitions() {
        List<ZBeanDefinition> result = Lists.newArrayList();
        try {
            for (String className : registyBeanClasses) {
                Class<?> beanClass = Class.forName(className);
                //如果是接口的话用他的实现类作为beanClassName
                if (beanClass.isInterface()) {
                    continue;
                }
                result.add(doCreateBeanDefinition(toLowerFirstCase(beanClass.getSimpleName()),beanClass.getName()));
//                result.add(doCreateBeanDefinition(beanClass.getName(),beanClass.getName()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    //把每一个配置信息解析成BeanDefinition
    private ZBeanDefinition doCreateBeanDefinition(String factoryBeanName, String className){
        try {

            ZBeanDefinition beanDefinition = new ZBeanDefinition();
            beanDefinition.setBeanClassName(className);
//            beanDefinition.setFactoryBeanName(toLowerFirstCase(beanClass.getSimpleName()));
            beanDefinition.setFactoryBeanName(factoryBeanName);
            return beanDefinition;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private String toLowerFirstCase(String simpleName) {
        char[] chars = simpleName.toCharArray();
        //大小写字母的ASCII码差32
        chars[0] += 32;
        return String.valueOf(chars);
    }
}
