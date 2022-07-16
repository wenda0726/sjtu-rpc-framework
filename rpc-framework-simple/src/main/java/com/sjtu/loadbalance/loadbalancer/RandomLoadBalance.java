package com.sjtu.loadbalance.loadbalancer;



import com.sjtu.loadbalance.AbstractLoadBalance;
import com.sjtu.remoting.dto.RpcRequest;

import java.util.List;
import java.util.Random;

/**
 * 负载均衡，随机选取一个服务节点
 * Implementation of random load balancing strategy
 *
 */
public class RandomLoadBalance extends AbstractLoadBalance {
    @Override
    protected String doSelect(List<String> serviceAddresses, RpcRequest rpcRequest) {
        Random random = new Random();
        return serviceAddresses.get(random.nextInt(serviceAddresses.size()));
    }
}
