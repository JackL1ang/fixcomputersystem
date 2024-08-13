package com.fixcomputer.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_order")
public class UserDetailInfo {
    @TableId(value = "order_id")
    private String orderId;
    private String name;
    private String phone;
    private String address;
    private String waybill;
    private String category;
    private String notes;
    private String userName;
    private String statusName;
    private String fixerId;
    private String create_date;
    private String accept_date;
    private String finish_date;
    @TableField("status")
    private Integer statusNum;
    private Integer whetherMail;
    private boolean rate_status;
    private String totalAmount;
    private String paymentDate;
    private boolean pay_status;

}

