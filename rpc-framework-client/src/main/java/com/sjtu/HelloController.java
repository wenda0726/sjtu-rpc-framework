package com.sjtu;

import com.sjtu.annotation.RpcReference;
import org.springframework.stereotype.Component;

@Component
public class HelloController {

    @RpcReference(version = "version1", group = "test1")
    private HelloService helloService;

    public void test() throws Exception{
        String hello = helloService.hello(new Hello("111", "222"));
        Thread.sleep(12000);
        Long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            System.out.println(helloService.hello(new Hello("111","222")));
            Long cost = System.currentTimeMillis() - start;
            System.out.println("远程调用耗时：" + cost);
        }
        
    }

}
