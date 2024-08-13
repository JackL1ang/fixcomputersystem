package com.fixcomputer.service.serviceImpl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fixcomputer.domain.dto.OrderInfo;
import com.fixcomputer.domain.entity.Order;
import com.fixcomputer.domain.vo.CompleteWaybill;
import com.fixcomputer.domain.vo.OrderDetailInfo;
import com.fixcomputer.domain.vo.UserUpdateOrder;
import com.fixcomputer.mapper.OrderUpdateMapper;
import com.fixcomputer.service.OrderRateService;
import com.fixcomputer.service.OrderSelectService;
import com.fixcomputer.service.OrderUpdateService;
import com.fixcomputer.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderUpdateServiceImpl extends ServiceImpl<OrderUpdateMapper, Order> implements OrderUpdateService {
    @Autowired
    OrderUpdateMapper orderUpdateMapper;

    @Autowired
    OrderRateService orderRateService;

    /**
     *
     * 更新订单为维修中状态
     *
     * */
    @Override
    public boolean UpdateFixStatus(OrderInfo orderInfo) {
        //1.查询状态
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("order_id",orderInfo.getOrder_id());
        Order order = orderUpdateMapper.selectOne(orderQueryWrapper);

        //2.修改状态
        UpdateWrapper<Order> orderDetailInfoUpdateWrapper = new UpdateWrapper<>();
        orderDetailInfoUpdateWrapper.eq("order_id",orderInfo.getOrder_id());
        order.setStatus(3);
        order.setCategory(orderInfo.getCategory());


        int update = orderUpdateMapper.update(order, orderDetailInfoUpdateWrapper);
        if(update==1){
            return true;
        }else {
            return false;
        }



    }

    /***
     *
     * 接收订单 （乐观锁）
     *
     */
    @Override
    public JSONObject acceptOrder(String order_id, String fix_id) {
        //1.先查询订单
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("order_id",order_id);
        Order order = orderUpdateMapper.selectOne(orderQueryWrapper);
        JSONObject jsonObject = new JSONObject();
        //2.将需要的值设置
        DateUtil dateUtil = new DateUtil();
        Date date = dateUtil.dateUtilForDate();
        UpdateWrapper<Order> updateWrapper = new UpdateWrapper<Order>();
        //条件order_id
        updateWrapper.eq("order_id",order_id);
        //修改订单状态
        order.setStatus(2);
        //修改维修员id
        order.setFixer_id(fix_id);
        //修改日期
        order.setAccept_date(date);
        int update = orderUpdateMapper.update(order, updateWrapper);
        if(update==1){
            jsonObject.put("status",true);
            return jsonObject;
        }else {
            jsonObject.put("status",false);
            return jsonObject;
        }

    }
    /**
     *
     *
     * 完成订单状态
     *
     * */

    public boolean updateOrderStatus(String order_id,int status){
        //1.查询数据
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("order_id",order_id);
        Order order = orderUpdateMapper.selectOne(orderQueryWrapper);

        //2.修改数据
        UpdateWrapper<Order> orderUpdateWrapper = new UpdateWrapper<>();
        DateUtil dateUtil = new DateUtil();
        //完成订单
        if(status == 0){
            orderUpdateWrapper.eq("order_id",order_id);

            order.setStatus(status);
            order.setEndTime(dateUtil.dateUtilForDate());
            int update = orderUpdateMapper.update(order, orderUpdateWrapper);
            //创建一个该订单的评价
            boolean orderRate = orderRateService.create_OrderRate(order_id);
            if(update == 1 && orderRate){
                return true;
            }else{
                return false;
            }
        }else{
            //修改其他状态
            orderUpdateWrapper.eq("order_id",order_id);
            order.setStatus(status);
            int update = orderUpdateMapper.update(order, orderUpdateWrapper);
            if(update == 1){
                return true;
            }else{
                return false;
            }
        }
    }

    @Override
    public boolean userUpdateOrderInfo(UserUpdateOrder userUpdateOrder, String user_id) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("order_id",userUpdateOrder.getOrderId());
        orderQueryWrapper.eq("user_id",user_id);
        Order order = orderUpdateMapper.selectOne(orderQueryWrapper);
        BeanUtil.copyProperties(userUpdateOrder, order);
        int update = orderUpdateMapper.update(order, orderQueryWrapper);
        if(update == 1){
            return true;
        }else {
            return false;
        }

    }

    @Override
    public boolean deleteOrder(String order_id, String user_id) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("user_id",user_id);
        orderQueryWrapper.eq("order_id",order_id);
        int delete = orderUpdateMapper.delete(orderQueryWrapper);
        if(delete == 1){
            return true;
        }else {
            return false;
        }

    }

    @Override
    public boolean fallBackOrder(String order_id) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("order_id",order_id);
        Order order = orderUpdateMapper.selectOne(orderQueryWrapper);

        UpdateWrapper<Order> orderUpdateWrapper = new UpdateWrapper<>();
        orderUpdateWrapper.eq("order_id",order_id);
        orderUpdateWrapper.set("fixer_id",null);
        orderUpdateWrapper.set("accept_date",null);
        order.setStatus(1);
        int update = orderUpdateMapper.update(order, orderUpdateWrapper);
        if(update==1) {
            return true;
        }else{
            return false;
        }

    }

    @Override
    public boolean completeWaybill(CompleteWaybill completeWaybill) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id",completeWaybill.getOrder_id());
        Order order = orderUpdateMapper.selectOne(queryWrapper);
        order.setCompleteWaybill(completeWaybill.getComplete_waybill());
        int update = orderUpdateMapper.update(order, queryWrapper);
        if(update==1){
            return true;
        }else {
            return false;
        }

    }


}
