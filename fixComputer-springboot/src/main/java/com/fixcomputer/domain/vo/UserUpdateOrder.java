package com.fixcomputer.domain.vo;


import lombok.Data;

@Data
public class UserUpdateOrder {
    private String orderId;

    private String category;

    private String address;

    private String name;

    private  String phone;

    private String waybill;

    private String notes;
}
