package com.fixcomputer.domain.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_userinfo")
public class UserAddress {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String userId;
    private String name;
    private String phone;
    private String province;
    private String city;
    private String area;
    private String address;
    @TableField(value = "default_address")
    private boolean defaultAddress;
}
