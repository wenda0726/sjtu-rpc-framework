package com.sjtu.serialize;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class Test  extends LengthFieldBasedFrameDecoder {
    public Test(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
    }
}
