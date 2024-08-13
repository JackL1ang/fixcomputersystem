package com.fixcomputer.domain.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("t_role")
@Data
public class Role {
    @TableId(value = "role_id")
    private Integer role_id;

    private String role_name;
}
