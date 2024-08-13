package com.fixcomputer.domain.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_order")
public class FixAcceptOrder {
    private String orderId;
    private String userName;
    private String phone;
    private String address;
    private String waybill;
    private String category;
    private String notes;
    private String create_date;
    private Integer whetherMail;


}
