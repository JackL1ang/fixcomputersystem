package com.fixcomputer.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_order")
public class OrderDetailInfo {
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
    private String rate_content;
    private String rate_score;
    private String complete_waybill;
    private String total_amount;
    private String paymentDate;
    private boolean payStatus;

}
