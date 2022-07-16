package com.sjtu.swd.rpc.netty.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Builder
@ToString
public class RpcResponse {
    private String message;
}