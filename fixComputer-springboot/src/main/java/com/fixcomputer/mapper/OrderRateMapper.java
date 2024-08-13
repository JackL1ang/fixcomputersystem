package com.fixcomputer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fixcomputer.domain.entity.Rate;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderRateMapper extends BaseMapper<Rate> {
}
