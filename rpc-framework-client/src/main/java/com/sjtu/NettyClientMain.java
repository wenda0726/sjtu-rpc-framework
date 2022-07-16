package com.sjtu;

import com.sjtu.annotation.RpcScan;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@RpcScan(basePackage = {"com.sjtu"})
public class NettyClientMain {
    public static void main(String[] args) throws Exception{
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(NettyClientMain.class);
        HelloController helloController = (HelloController) applicationContext.getBean("helloController");
        helloController.test();
    }
}
