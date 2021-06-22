package com.nisq.mybatis.redis.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

/**
 * @author nsq
 * @Title:
 * @Package
 * @Description:
 * @date 2021-05-1714:19
 */
public class ConfigBeanFactoryPostProcessor implements BeanFactoryPostProcessor, BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("start ---perPostProcessor");
        BeanDefinition per = beanFactory.getBeanDefinition("per");
//        per.getPropertyValues().addPropertyValue("age","170");
        System.out.println("end ---perPostProcessor");
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        System.out.println("BeanDefinitionRegistry-------");
    }
}
