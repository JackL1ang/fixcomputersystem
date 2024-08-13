package com.fixcomputer.domain.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_qrcode")
public class QRCode {
    @TableId
    private String orderId;

    private String url;

    private String guaranteeDate;

}
