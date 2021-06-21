package com.nisq.mybatis.zookeeper.locks;

import com.nisq.mybatis.zookeeper.config.utils.ZKUtils;
import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author nsq
 * @Title:
 * @Package
 * @Description:
 * @date 2021-05-289:45
 */
public class TestLock {


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
    public void testLock() {

        for (int i = 0; i < 10; i++) {
            new Thread() {
                @Override
                public void run() {
                    LockWatchCallBack watchCallBack = new LockWatchCallBack();

                    watchCallBack.setZk(zk);
                    String name = Thread.currentThread().getName();
                    watchCallBack.setThreadName(name);

                    watchCallBack.tryLock();

                    System.out.println(name+":working");

                    try {
                        Thread.sleep(1000);//
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    watchCallBack.unLock();


                }
            }.start();
        }

        while (true) {


        }
    }
}
