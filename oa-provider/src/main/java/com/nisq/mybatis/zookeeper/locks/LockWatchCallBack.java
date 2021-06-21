package com.nisq.mybatis.zookeeper.locks;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author nsq
 * @Title:
 * @Package
 * @Description: 分布式锁实现
 * @date 2021-05-2810:03
 */
public class LockWatchCallBack implements Watcher, AsyncCallback.StringCallback, AsyncCallback.Children2Callback , AsyncCallback.StatCallback {


    ZooKeeper zk;

    String threadName;

    String pathName;

    CountDownLatch cc = new CountDownLatch(1);

    public ZooKeeper getZk() {
        return zk;
    }

    public void setZk(ZooKeeper zk) {
        this.zk = zk;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public void tryLock() {

        System.out.println(threadName + "> create...");
        zk.create("/lock", threadName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL, this, threadName);
        try {
            cc.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void unLock() {
        try {
            zk.delete(pathName,-1);
            System.out.println("i exit ");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void process(WatchedEvent event) {
        switch (event.getType()) {
            case None:
                break;
            case NodeCreated:
                break;
            case NodeDeleted:
                zk.getChildren("/",false,this,threadName);
                break;
            case NodeDataChanged:
                break;
            case NodeChildrenChanged:
                break;
        }

    }

    @Override
    public void processResult(int rc, String path, Object ctx, String name) {
        if (name != null) {

            System.out.println(threadName + "> create.node.." + name);
            pathName = name;

            zk.getChildren("/", false, this, threadName);
        } else {
            System.out.println(threadName + "> create.node.. fail");
        }

    }

    @Override
    public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
        if (children != null) {
            Collections.sort(children);

            int i = children.indexOf( pathName.substring(1));
            if (i == 0) {
                System.out.println("i am first "+threadName);
                cc.countDown();
            }else {
                zk.exists("/"+children.get(i-1),this,this,threadName);
            }
        }
    }
    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {

    }
}
