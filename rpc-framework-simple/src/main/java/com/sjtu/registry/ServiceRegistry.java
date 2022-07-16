package com.sjtu.registry;



import swd.extension.SPI;

import java.net.InetSocketAddress;

/**
 * service registration
 *
 */
@SPI
public interface ServiceRegistry {
    /**
     * 服务注册接口，向zookeeper服务注册中心添加服务
     * register service
     *
     * @param rpcServiceName    rpc service name
     * @param inetSocketAddress service address
     */
    void registerService(String rpcServiceName, InetSocketAddress inetSocketAddress);

}
