package com.fixcomputer.domain.vo;


import lombok.Data;

@Data
public class OrderStatus {
    private String orderId;

    private int status;

    private String statusName;

}
