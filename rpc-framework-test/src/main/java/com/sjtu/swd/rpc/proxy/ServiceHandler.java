package com.sjtu.swd.rpc.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ServiceHandler implements InvocationHandler {

    //代理类的真实对象
    private  final Object target;

    public ServiceHandler(Object target){
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //封装一些逻辑信息，在调用直接进行功能增强，比如日志打印，rpc网络通信的细节隐藏
        System.out.println("before method " + method.getName());
        //执行方法
        Object result = method.invoke(target, args);
        System.out.println("after method " + method.getName());
        return result;
    }
}
