package com.fixcomputer.domain.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ForumInfo {

    private String sendId;

    private String title;

    private String content;

    private String createTime;

    private String author;

    private Long comment_size;
}
