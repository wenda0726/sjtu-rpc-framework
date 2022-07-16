package com.sjtu.swd.rpc.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;

public class Test {
    public static void main(String[] args) throws Exception {
        CuratorFramework zkClient = ZkClient.zkStart();

        System.out.println(zkClient.checkExists().forPath("/node1/00001"));//不为null的话，说明节点创建成功);

    }
}
