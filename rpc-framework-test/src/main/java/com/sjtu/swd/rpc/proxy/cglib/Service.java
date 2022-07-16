package com.sjtu.swd.rpc.proxy.cglib;

public class Service {
    public void send(String message){
        System.out.println("send the message is : ["+message+"]");
    }
}
