package com.sjtu.swd.rpc.proxy;

public class ServiceImpl implements Service {
    @Override
    public void send(String message) {
        System.out.println("send the message is : "+message+"");
    }
}
