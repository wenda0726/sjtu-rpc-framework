package com.sjtu.remoting.dto;


import lombok.*;

/**
 * @author wangtao
 * @createTime 2020年10月2日 12:33
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RpcMessage {

    /**
     * rpc message type
     */
    private byte messageType;
    /**
     * serialization type
     * 序列化类型
     */
    private byte codec;
    /**
     * compress type
     */
    private byte compress;
    /**
     * request id
     */
    private int requestId;
    /**
     * request data
     */
    private Object data;

}
