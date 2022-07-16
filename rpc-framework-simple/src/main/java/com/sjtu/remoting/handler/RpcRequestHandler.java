package com.sjtu.remoting.handler;


import com.sjtu.provider.ServiceProvider;
import com.sjtu.provider.impl.ZkServiceProviderImpl;
import com.sjtu.remoting.dto.RpcRequest;
import lombok.extern.slf4j.Slf4j;
import swd.exception.RpcException;
import swd.factory.SingletonFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * RpcRequest processor
 *
 * @author shuang.kou
 * @createTime 2020年05月13日 09:05:00
 */
@Slf4j
public class RpcRequestHandler {
    private final ServiceProvider serviceProvider;

    public RpcRequestHandler() {
        serviceProvider = SingletonFactory.getInstance(ZkServiceProviderImpl.class);
    }

    /**
     * 处理客户端需要的方法，然后返回结果
     * Processing rpcRequest: call the corresponding method, and then return the method
     */
    public Object handle(RpcRequest rpcRequest) {
        //服务发现，通过服务名字，找到能够处理信息的服务（提供方法的对象）
        Object service = serviceProvider.getService(rpcRequest.getRpcServiceName());
        return invokeTargetMethod(rpcRequest, service);
    }

    /**
     * 获得方法执行的结果
     * get method execution results
     *
     * @param rpcRequest client request
     * @param service    service object 服务对象
     *
     * @return the result of the target method execution
     */
    private Object invokeTargetMethod(RpcRequest rpcRequest, Object service) {
        Object result;
        try {
            //通过方法名字，和参数class获取对应的方法
            Method method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
            //方法的执行调用，service执行方法的对象，参数
            result = method.invoke(service, rpcRequest.getParameters());
            log.info("service:[{}] successful invoke method:[{}]", rpcRequest.getInterfaceName(), rpcRequest.getMethodName());
        } catch (NoSuchMethodException | IllegalArgumentException | InvocationTargetException | IllegalAccessException e) {
            throw new RpcException(e.getMessage(), e);
        }
        return result;
    }
}
