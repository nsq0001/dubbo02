package com.nisq.mybatis.zookeeper.config.utils;

import com.nisq.mybatis.zookeeper.config.ZKWatch.DefaultWatch;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * @author nsq
 * @Title:
 * @Package
 * @Description:
 * @date 2021-05-2710:54
 */
public class ZKUtils {

    private static ZooKeeper zk;

    private static DefaultWatch defaultWatch = new DefaultWatch();

    private static CountDownLatch cc = new CountDownLatch(1);

    public static ZooKeeper getZk(){

        try {
            zk = new ZooKeeper("192.168.0.74/testLock",2000,defaultWatch);
            defaultWatch.setCc(cc);
            cc.await();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return zk;
    }
}
