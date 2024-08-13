package com.fixcomputer.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fixcomputer.domain.entity.Order;
import com.fixcomputer.domain.vo.OrderDetailInfo;
import com.fixcomputer.domain.vo.FixAcceptOrder;
import com.fixcomputer.domain.vo.OrderStatus;

import java.util.List;
import java.util.Map;

public interface OrderSelectService extends IService<Order> {
    boolean addOrder(Order order);

    JSONObject selectAllOrder();

    OrderDetailInfo selectByOrderId(String uid);

    JSONObject selectOrderStatus(int status);

    JSONObject selectOrderByUserId(String id);

    JSONObject selectOrderByFixerId(String id);

    List<OrderStatus> selectIncompleteOrderById(String user_id);

    List<OrderStatus> selectIncompleteOrderAll();

    List<OrderStatus> userFixOrderStatus(String user_id);

    String guaranteeDate(String order_id);


}
