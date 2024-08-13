package com.fixcomputer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fixcomputer.domain.entity.OrderPayInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PayInfoMapper extends BaseMapper<OrderPayInfo> {
}
