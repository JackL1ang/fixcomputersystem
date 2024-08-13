package com.fixcomputer.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_payinfo")
public class OrderPayInfo {
    @TableId(value = "order_id")
    private String orderId;

    private Double totalAmount;

    private String paymentDate;

    private boolean status;
}
