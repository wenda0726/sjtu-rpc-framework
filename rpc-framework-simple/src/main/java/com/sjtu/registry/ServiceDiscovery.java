package com.sjtu.registry;



import com.sjtu.remoting.dto.RpcRequest;
import swd.extension.SPI;

import java.net.InetSocketAddress;

/**
 * service discovery
 */
@SPI
public interface ServiceDiscovery {
    /**
     * 通过rpcRequest可以获取到对应服务提供端的地址信息
     * lookup service by rpcServiceName
     *
     * @param rpcRequest rpc service pojo
     * @return service address
     */
    InetSocketAddress lookupService(RpcRequest rpcRequest);
}
