package com.fixcomputer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fixcomputer.domain.entity.UserAddress;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserAddressInfoMapper extends BaseMapper<UserAddress> {
}
