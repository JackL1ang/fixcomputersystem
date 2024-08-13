package com.fixcomputer.domain.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_useparts")
public class UsePartsCount {

    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    @TableField(value = "order_id")
    private String order_id;

    @TableField(value = "parts_id")
    private String partsId;

    private int amount;


}
