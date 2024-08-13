package com.fixcomputer.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@TableName("t_order")
public class Order {

    @TableId(value = "order_id")
    private String order_id ;

    private String user_id;

    private String fixer_id;

    private String name;

    private String phone;

    private String address;

    private String waybill;

    private String category;

    private String notes;

    private Integer status;

    @TableField("create_date")
    private Date startTime;

    private Date accept_date;

    @TableField("finish_date")
    private Date endTime;
    @Version
    private int version;

    @TableField("complete_waybill")
    private String completeWaybill;


}
