package com.nisq.mybatis.redis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author nsq
 * @Title:
 * @Package
 * @Description:
 * @date 2021-05-179:43
 */
public class TestPerson {

    @Test
    public void test1() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("person.xml");
        Person per = ctx.getBean(Person.class);

        System.out.println(per.toString());
    }
}
