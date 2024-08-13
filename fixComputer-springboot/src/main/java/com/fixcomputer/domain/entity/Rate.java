package com.fixcomputer.domain.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_rate")
public class Rate {

    @TableId(value = "rate_id",type = IdType.AUTO)
    private Integer rateId;

    @TableField(value = "order_id")
    private String orderId;

    @TableField(value = "rate_score")
    private int rateScore;

    @TableField(value="rate_content")
    private String rateContent;

    @TableField(value = "rate_status")
    private boolean rateStatus;

    @TableField(value = "create_time")
    private String createTime;

}
