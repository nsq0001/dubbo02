package com.nisq.mybatis.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Random;

/**
 * @author nsq
 * @Title:
 * @Package
 * @Description:
 * @date 2021-05-1416:10
 */
@Component
public class TestRedis {


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    @Qualifier("ooxx")
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    ObjectMapper objectMapper;


    public void testRedis(){
        //字符串类型
//        redisTemplate.opsForValue().set("k1","hello");//存在ascii
        stringRedisTemplate.opsForValue().set("k2","strhello");//去除ascii
//        System.out.println(redisTemplate.opsForValue().get("k1"));
        System.out.println(stringRedisTemplate.opsForValue().get("k2"));

        //hashmap
        stringRedisTemplate.opsForHash().put("nisq","name","nishiquan");
        stringRedisTemplate.opsForHash().put("nisq","age",18);
        Map<Object, Object> map = stringRedisTemplate.opsForHash().entries("nisq");
        Person person = objectMapper.convertValue(map, Person.class);
        System.out.println(person.toString());

        //消息发布订阅
        RedisConnection redisConnection = stringRedisTemplate.getConnectionFactory().getConnection();
        redisConnection.subscribe(new MessageListener() {
            @Override
            public void onMessage(Message message, byte[] bytes) {
                System.out.println(new String(message.getBody()));
            }
        },"xxoo".getBytes());

        for(;;){
            try {
                Thread.sleep(3000);
                stringRedisTemplate.convertAndSend("ooxx","hello:"+new Random(100).nextInt());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
