package com.sjtu.swd.rpc.socket;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Message implements Serializable {

    private String content;
}
