package com.fixcomputer.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@TableName("t_notice")
@Data
public class Notice {
    @TableId(value = "id",type = IdType.AUTO)
    private String id;
    private String title;
    private String content;
    @TableField(value = "create_time")
    private String createTime;
    private String author;
}
