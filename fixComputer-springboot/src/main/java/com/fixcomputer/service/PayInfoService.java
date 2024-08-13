package com.fixcomputer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fixcomputer.domain.entity.Order;
import com.fixcomputer.domain.entity.OrderPayInfo;

import java.util.List;

public interface PayInfoService extends IService<OrderPayInfo> {



    boolean insertOrderPayInfo(OrderPayInfo orderPayInfo);


    boolean updateOrderPayInfo(String order_id,String create_date);







}
