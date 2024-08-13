package com.fixcomputer.domain.dto;


import com.baomidou.mybatisplus.annotation.TableName;
import com.fixcomputer.domain.entity.UsePartsCount;
import lombok.Data;

import java.util.List;

@Data
public class FixInfo {

    private String order_id;

    private String fix_source;

    private String fix_text;

    private boolean whether_fix;

    private double amount;

    private List<UsePartsCount> parts;
}
