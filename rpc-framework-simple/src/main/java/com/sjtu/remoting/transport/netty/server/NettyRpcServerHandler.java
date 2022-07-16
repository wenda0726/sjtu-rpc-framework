package com.sjtu.remoting.transport.netty.server;


import com.sjtu.remoting.constants.RpcConstants;
import com.sjtu.remoting.dto.RpcMessage;
import com.sjtu.remoting.dto.RpcRequest;
import com.sjtu.remoting.dto.RpcResponse;
import com.sjtu.remoting.handler.RpcRequestHandler;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import swd.enums.CompressTypeEnum;
import swd.enums.RpcResponseCodeEnum;
import swd.enums.SerializationTypeEnum;
import swd.factory.SingletonFactory;

/**
 * Customize the ChannelHandler of the server to process the data sent by the client.
 * <p>
 * 如果继承自 SimpleChannelInboundHandler 的话就不要考虑 ByteBuf 的释放 ，{@link SimpleChannelInboundHandler} 内部的
 * channelRead 方法会替你释放 ByteBuf ，避免可能导致的内存泄露问题。详见《Netty进阶之路 跟着案例学 Netty》
 *
 * @author shuang.kou
 * @createTime 2020年05月25日 20:44:00
 */
@Slf4j
public class NettyRpcServerHandler extends ChannelInboundHandlerAdapter {


    private final RpcRequestHandler rpcRequestHandler;

    public NettyRpcServerHandler() {
        this.rpcRequestHandler = SingletonFactory.getInstance(RpcRequestHandler.class);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            if (msg instanceof RpcMessage) {
                //服务端收到的客户端信息RpcMessage
                log.info("server receive msg: [{}] ", msg);
                byte messageType = ((RpcMessage) msg).getMessageType();
                //创建服务端处理的结果消息
                RpcMessage rpcMessage = new RpcMessage();
                //设置序列化类型为HESSIAN
                rpcMessage.setCodec(SerializationTypeEnum.HESSIAN.getCode());
                rpcMessage.setCompress(CompressTypeEnum.GZIP.getCode());
                if (messageType == RpcConstants.HEARTBEAT_REQUEST_TYPE) {
                    //如果收到的msg是心跳信息，则回复一个心跳信息
                    rpcMessage.setMessageType(RpcConstants.HEARTBEAT_RESPONSE_TYPE);
                    rpcMessage.setData(RpcConstants.PONG);
                } else {
                    //正常处理收到的RpcMessage
                    RpcRequest rpcRequest = (RpcRequest) ((RpcMessage) msg).getData();
                    // Execute the target method (the method the client needs to execute) and return the method result
                    //服务端处理客户端需要调用执行的方法，通过rpcRequestHandler去处理
                    Object result = rpcRequestHandler.handle(rpcRequest);
                    //方法调用结果
                    log.info(String.format("server get result: %s", result.toString()));
                    //设置rpc信息类型为Response
                    rpcMessage.setMessageType(RpcConstants.RESPONSE_TYPE);
                    if (ctx.channel().isActive() && ctx.channel().isWritable()) {
                        //设置返回的信息，（成功格式）
                        RpcResponse<Object> rpcResponse = RpcResponse.success(result, rpcRequest.getRequestId());
                        rpcMessage.setData(rpcResponse);
                    } else {
                        //设置返回的信息，（失败格式）
                        RpcResponse<Object> rpcResponse = RpcResponse.fail(RpcResponseCodeEnum.FAIL);
                        rpcMessage.setData(rpcResponse);
                        log.error("not writable now, message dropped");
                    }
                }
                //返回的信息
                ctx.writeAndFlush(rpcMessage).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }
        } finally {
            //Ensure that ByteBuf is released, otherwise there may be memory leaks
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.READER_IDLE) {
                log.info("idle check happen, so close the connection");
                ctx.close();
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("server catch exception");
        cause.printStackTrace();
        ctx.close();
    }
}
