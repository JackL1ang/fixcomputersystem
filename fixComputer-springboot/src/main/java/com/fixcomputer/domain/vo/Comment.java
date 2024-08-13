package com.fixcomputer.domain.vo;

import com.fixcomputer.utils.DateUtil;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Data
public class Comment {

    private String sendId;

    private String content;

    private String commentId;

    private String createTime;

    private String author;



}
