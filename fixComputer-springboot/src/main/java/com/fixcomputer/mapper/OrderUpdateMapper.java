package com.fixcomputer.mapper;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fixcomputer.domain.entity.Order;
import com.fixcomputer.domain.vo.OrderDetailInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderUpdateMapper extends BaseMapper<Order> {

}
