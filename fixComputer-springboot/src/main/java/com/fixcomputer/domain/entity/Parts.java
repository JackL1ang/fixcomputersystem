package com.fixcomputer.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_parts")
public class Parts {
    @TableId(value = "parts_id")
    private String partsId;
    private String manufacturer;
    private String name;
    private double price;
    private int inventory;
    private String createDate;
    private String operator;

}
