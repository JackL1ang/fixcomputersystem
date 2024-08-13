package com.fixcomputer.domain.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
@TableName ("t_order")
@Data
public class OrderMessage {
    private String orderId;
    private Integer fixer_id;
    private String category;
    private String userName;
    private int status;
    private String create_date;
    private String accept_date;
    private String finish_date;
}
