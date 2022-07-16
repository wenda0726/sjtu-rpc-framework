package com.sjtu.impl;

import com.sjtu.Hello;
import com.sjtu.HelloService;
import com.sjtu.annotation.RpcService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RpcService(version = "version1",group = "test1")
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(Hello hello) {
        log.info("HelloServiceImpl收到: {}.", hello.getMessage());
        String result = "Hello description is " + hello.getDescription();
        log.info("HelloServiceImpl返回: {}.", result);
        return result;
    }
}
