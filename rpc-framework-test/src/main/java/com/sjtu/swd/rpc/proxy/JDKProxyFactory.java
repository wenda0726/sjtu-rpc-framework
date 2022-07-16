package com.sjtu.swd.rpc.proxy;

import java.lang.reflect.Proxy;

public class JDKProxyFactory {
    //获取一个代理对象
    public static Object getProxy(Object target){
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new ServiceHandler(target));
    }
}
