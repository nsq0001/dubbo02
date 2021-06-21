package com.nisq.mybatis.zookeeper.config;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

/**
 * @author nsq
 * @Title:
 * @Package
 * @Description:
 * @date 2021-05-2616:50
 */
public class App {

    public static void main(String[] args) throws Exception {
        System.out.println("hello world");


        CountDownLatch cd = new CountDownLatch(1);
        //zk是有session概念的，没有连接池的概念
        //watch 观察，回调
        //watch的注册值发生在 读类型调用，get，exites。。。
        //第一类：new zk 的时候，传入的watch，这个watch是session级别的，跟path，node没有关系
        ZooKeeper zk = new ZooKeeper("192.168.0.74", 3000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                Event.KeeperState state = event.getState();
                Event.EventType type = event.getType();
                String path = event.getPath();
                System.out.println("new zk watck:"+event.toString());
                System.out.println("state:" + state + " type:" + type);
                switch (state) {
                    case Unknown:
                        break;
                    case Disconnected:
                        break;
                    case NoSyncConnected:
                        break;
                    case SyncConnected:
                        System.out.println("connected...");
                        cd.countDown();
                        break;
                    case AuthFailed:
                        break;
                    case ConnectedReadOnly:
                        break;
                    case SaslAuthenticated:
                        break;
                    case Expired:
                        break;
                }
                switch (type) {
                    case None:
                        break;
                    case NodeCreated:
                        break;
                    case NodeDeleted:
                        break;
                    case NodeDataChanged:
                        break;
                    case NodeChildrenChanged:
                        break;
                }
            }
        });
        cd.await();
        ZooKeeper.States state = zk.getState();
        switch (state) {
            case CONNECTING:
                System.out.println("ing...");
                break;
            case ASSOCIATING:
                break;
            case CONNECTED:
                System.out.println("CONNECTED...");
                break;
            case CONNECTEDREADONLY:
                break;
            case CLOSED:
                break;
            case AUTH_FAILED:
                break;
            case NOT_CONNECTED:
                break;
        }

        String pathName = zk.create("/ooxx", "olddata".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("pathname:"+pathName);

        final Stat stat = new Stat();

        byte[] data = zk.getData("/ooxx", new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("getdata watch :" + event.toString());
            }
        }, stat);

        System.out.println(data.toString());

        Stat stat1 = zk.setData("/ooxx", "newdata".getBytes(), 0);
        Stat stat2 = zk.setData("/ooxx", "newdata1".getBytes(), stat1.getVersion());

        //异步监控
        System.out.println("getdata call...start");
        zk.getData("/ooxx", false, new AsyncCallback.DataCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
                System.out.println("getdata call..."+ctx);
            }
        },"NISQ");
        System.out.println("getdata call...end");

        Thread.sleep(33333333);
    }
}
