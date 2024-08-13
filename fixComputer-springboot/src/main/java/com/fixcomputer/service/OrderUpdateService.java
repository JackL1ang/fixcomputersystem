package com.fixcomputer.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fixcomputer.domain.dto.OrderInfo;
import com.fixcomputer.domain.entity.Order;
import com.fixcomputer.domain.vo.CompleteWaybill;
import com.fixcomputer.domain.vo.OrderDetailInfo;
import com.fixcomputer.domain.vo.UserUpdateOrder;

public interface OrderUpdateService extends IService<Order> {
   boolean UpdateFixStatus(OrderInfo orderInfo);

   JSONObject acceptOrder(String order_id, String fix_id);

   boolean updateOrderStatus(String order_id,int status);

   boolean userUpdateOrderInfo(UserUpdateOrder userUpdateOrder,String user_id);

   boolean deleteOrder(String order_id,String user_id);

   boolean fallBackOrder(String order_id);

   boolean completeWaybill(CompleteWaybill completeWaybill);


}
