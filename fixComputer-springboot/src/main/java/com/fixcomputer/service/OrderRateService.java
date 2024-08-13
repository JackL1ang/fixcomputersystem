package com.fixcomputer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fixcomputer.domain.entity.Rate;

public interface OrderRateService extends IService<Rate> {

    boolean create_OrderRate(String order_id);

    boolean update_OrderRate(Rate rate);
}
