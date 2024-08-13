package com.fixcomputer.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_fixinfo")
public class RepairInfo {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField(value = "order_id")
    private String orderId;
    @TableField(value = "fix_source")
    private String fixSource;
    @TableField(value = "fix_text")
    private String fixText;
    @TableField(value = "whether_fix")
    private boolean whetherFix;
    @TableField(value = "create_date")
    private String createDate;
}
