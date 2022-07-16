package com.sjtu.swd.rpc.proxy.cglib;

public class Test {
    public static void main(String[] args) {
        Service send = (Service)CglibProxyFactory.getProxy(new Service().getClass());
        send.send("swd gy");
    }
}
