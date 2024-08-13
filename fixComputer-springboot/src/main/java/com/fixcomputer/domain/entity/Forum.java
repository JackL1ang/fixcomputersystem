package com.fixcomputer.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_forum")
public class Forum {
    @TableId("send_id")
    private String sendId;

    private String title;

    private String content;

    private Date createTime;

    private String author;




}
