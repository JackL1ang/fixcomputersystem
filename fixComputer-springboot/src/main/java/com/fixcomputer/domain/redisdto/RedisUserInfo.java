package com.fixcomputer.domain.redisdto;


import lombok.Data;

@Data
public class RedisUserInfo {
    private  Long  id;

    private String username;

    private String password;

    private String role_name;

    private  byte state;
}
