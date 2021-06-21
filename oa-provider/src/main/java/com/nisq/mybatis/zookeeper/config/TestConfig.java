package com.nisq.mybatis.zookeeper.config;

import com.nisq.mybatis.zookeeper.config.ZKWatch.WatchCallBack;
import com.nisq.mybatis.zookeeper.config.utils.ZKUtils;
import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author nsq
 * @Title:
 * @Package
 * @Description: 分布式配置中心
 * @date 2021-05-2710:54
 */
public class TestConfig {


    private static ZooKeeper zk;


    @Before
    public void getConn() {
        zk = ZKUtils.getZk();
    }


    @After
    public void close() {
        try {
            zk.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void testConf() {

        WatchCallBack watchCallBack = new WatchCallBack();

        watchCallBack.setZk(zk);

        MyConfig myConfig = new MyConfig();

        watchCallBack.setConfig(myConfig);

        watchCallBack.await();


        while (true) {

            if (myConfig.getConf().equals("")) {
                System.out.println("node diu le ...");
                watchCallBack.await();
            } else {
                System.out.println(myConfig.getConf());
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
