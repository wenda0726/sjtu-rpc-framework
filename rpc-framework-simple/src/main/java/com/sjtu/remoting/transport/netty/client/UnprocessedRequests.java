package com.sjtu.remoting.transport.netty.client;



import com.sjtu.remoting.dto.RpcResponse;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * unprocessed requests by the server.
 *
 * @author shuang.kou
 * @createTime 2020年06月04日 17:30:00
 */
public class UnprocessedRequests {
    //使用CompletableFuture异步处理未完成的请求
    //key为rpcRequest的ID，value为CompletableFuture对象
    //在调用过程中，需要异步的统计收到的rpcResponse是否已经被执行完毕，所以使用了CompletableFuture
    private static final Map<String, CompletableFuture<RpcResponse<Object>>> UNPROCESSED_RESPONSE_FUTURES = new ConcurrentHashMap<>();

    public void put(String requestId, CompletableFuture<RpcResponse<Object>> future) {
        UNPROCESSED_RESPONSE_FUTURES.put(requestId, future);
    }

    public void complete(RpcResponse<Object> rpcResponse) {
        //将一个未完成的请求进行处理
        CompletableFuture<RpcResponse<Object>> future = UNPROCESSED_RESPONSE_FUTURES.remove(rpcResponse.getRequestId());
        if (null != future) {
            future.complete(rpcResponse);
        } else {
            throw new IllegalStateException();
        }
    }
}
