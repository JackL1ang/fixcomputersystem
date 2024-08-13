package com.fixcomputer.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_map")
public class MapCoordinate {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String name;
    private String address;
    private String coordinate;
}