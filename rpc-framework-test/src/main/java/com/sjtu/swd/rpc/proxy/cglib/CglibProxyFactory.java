package com.sjtu.swd.rpc.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;

public class CglibProxyFactory {
    public static Object getProxy(Class<?> clazz){
        //通过Enhancer 创建需要被增强的对象
        Enhancer enhancer = new Enhancer();
        //设置类加载器
        enhancer.setClassLoader(clazz.getClassLoader());
        //设置被代理的类
        enhancer.setSuperclass(clazz);
        //设置自定义的MethodInterceptor
        enhancer.setCallback(new ServiceMethodInterceptor());
        return enhancer.create();
    }
}
