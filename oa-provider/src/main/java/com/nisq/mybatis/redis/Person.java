package com.nisq.mybatis.redis;

/**
 * @author nsq
 * @Title:
 * @Package
 * @Description:
 * @date 2021-05-1510:09
 */
public class Person {
    String name;
    String age;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void initM(){
        System.out.println("执行 init 方法");
    }
}
