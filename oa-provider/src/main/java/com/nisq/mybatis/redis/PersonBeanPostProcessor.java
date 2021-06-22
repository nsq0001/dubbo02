package com.nisq.mybatis.redis;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author nsq
 * @Title:
 * @Package
 * @Description:
 * @date 2021-05-1812:56
 */
public class PersonBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("person 初始化之前---"+ ((Person)bean).toString());
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("person 初始化之后"+((Person)bean).toString());
        return null;
    }
}
