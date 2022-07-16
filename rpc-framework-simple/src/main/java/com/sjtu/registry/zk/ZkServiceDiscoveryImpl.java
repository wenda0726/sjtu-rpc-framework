package com.sjtu.registry.zk;


import com.sjtu.loadbalance.LoadBalance;
import com.sjtu.registry.ServiceDiscovery;
import com.sjtu.registry.zk.util.CuratorUtils;
import com.sjtu.remoting.dto.RpcRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import swd.enums.RpcErrorMessageEnum;
import swd.exception.RpcException;
import swd.extension.ExtensionLoader;
import swd.utils.CollectionUtil;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * 服务发现
 * service discovery based on zookeeper
 *
 */
@Slf4j
public class ZkServiceDiscoveryImpl implements ServiceDiscovery {
    private final LoadBalance loadBalance;

    public ZkServiceDiscoveryImpl() {
        this.loadBalance = ExtensionLoader.getExtensionLoader(LoadBalance.class).getExtension("loadBalance");
    }

    @Override
    public InetSocketAddress lookupService(RpcRequest rpcRequest) {
        String rpcServiceName = rpcRequest.getRpcServiceName();
        CuratorFramework zkClient = CuratorUtils.getZkClient();
        List<String> serviceUrlList = CuratorUtils.getChildrenNodes(zkClient, rpcServiceName);
        if (CollectionUtil.isEmpty(serviceUrlList)) {
            //服务没找到
            throw new RpcException(RpcErrorMessageEnum.SERVICE_CAN_NOT_BE_FOUND, rpcServiceName);
        }
        // load balancing
        //负载均衡策略，随机/参考dubbo实现的负载均衡策略
        String targetServiceUrl = loadBalance.selectServiceAddress(serviceUrlList, rpcRequest);
        log.info("Successfully found the service address:[{}]", targetServiceUrl);
        String[] socketAddressArray = targetServiceUrl.split(":");
        String host = socketAddressArray[0];
        int port = Integer.parseInt(socketAddressArray[1]);
        //获取到的主机地址，端口号
        return new InetSocketAddress(host, port);
    }
}
