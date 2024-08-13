package com.fixcomputer.domain.dto;

import lombok.Data;

@Data
public class UserOrderInfo {
    private String order_id;
    private String category;
    private String name;
    private String phone;
    private String waybill;
    private String notes;
}
