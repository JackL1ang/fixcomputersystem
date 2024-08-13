package com.fixcomputer.ws.pojo;

import lombok.Data;

@Data
public class Message {
    private String myName;

    private String toName;

    private String message;

    private String uuid;
}
