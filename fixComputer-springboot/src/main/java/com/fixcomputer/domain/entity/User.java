package com.fixcomputer.domain.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


import java.io.Serializable;

@TableName("t_user")
@Data
public class User implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private  String username;

    private String password;

    private Integer role;

    private byte state;



}
