package com.fixcomputer.domain.vo;


import lombok.Data;

@Data
public class UserInfo {

    private String id;

    private String username;

    private String password;

    private Integer role;

    private String name;

    private String phone;

    private String infoId;

    private String province;

    private String city;

    private String area;

    private String address;

    private byte state;
}
