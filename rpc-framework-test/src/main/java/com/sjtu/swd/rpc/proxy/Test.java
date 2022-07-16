package com.sjtu.swd.rpc.proxy;

public class Test {
    public static void main(String[] args) {
        Service service = (Service) JDKProxyFactory.getProxy(new ServiceImpl());
        service.send("swd yyds");
    }
}
