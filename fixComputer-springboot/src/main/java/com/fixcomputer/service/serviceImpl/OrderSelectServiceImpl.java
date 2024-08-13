package com.fixcomputer.service.serviceImpl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fixcomputer.domain.entity.CompleteDate;
import com.fixcomputer.domain.entity.Order;
import com.fixcomputer.domain.entity.QRCode;
import com.fixcomputer.domain.vo.OrderDetailInfo;
import com.fixcomputer.domain.vo.FixAcceptOrder;
import com.fixcomputer.domain.vo.OrderStatus;
import com.fixcomputer.domain.vo.UserDetailInfo;
import com.fixcomputer.mapper.OrderSelectMapper;
import com.fixcomputer.service.OrderSelectService;
import com.fixcomputer.service.PageChangeService;
import com.fixcomputer.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.simpleframework.xml.core.Complete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class OrderSelectServiceImpl extends ServiceImpl<OrderSelectMapper, Order> implements OrderSelectService {

    @Autowired
    private OrderSelectMapper orderSelectMapper;

    @Autowired
    PageChangeService pageChangeService;



    /**
     *
     * 新增订单
     *
     * */
    @Override
    public boolean addOrder(Order order) {
        int insert = orderSelectMapper.insert(order);
        if(insert == 1){
            return true;
        }
        else{
            return false;
        }
    }

    /***
     *
     *
     * 管理员查询所有订单
     *
     *
     */
    @Override
    public JSONObject selectAllOrder() {
        JSONObject jsonObject = new JSONObject();
        try{
            //获取第一页的页面
            List<OrderDetailInfo> orderDetailInfosPages = pageChangeService.orderPageChangeForAdmin(1);
            List<OrderDetailInfo> orderDetailInfos = orderSelectMapper.selectAllOrder();
            jsonObject.put("state",true);
            //分页后订单
            jsonObject.put("order", orderDetailInfosPages);
            //所有订单
            jsonObject.put("orderall",orderDetailInfos);
            return jsonObject;
        }catch (Exception e){
            log.info(String.valueOf(e));
            jsonObject.put("state",false);
            return jsonObject;

        }
    }


    /**
     *
     * 使用订单号查询
     *
     * */
    @Override
    public OrderDetailInfo selectByOrderId(String uid) {

        OrderDetailInfo orderDetailInfo = orderSelectMapper.selectByOrderId(uid);

        return orderDetailInfo;

    }

    /**
     *
     *
     * 查询订单状态
     *
     * */
    @Override
    public JSONObject selectOrderStatus(int status) {
        List<FixAcceptOrder> fixOrders = orderSelectMapper.selectFixOrder(status);
        JSONObject jsonObject = new JSONObject();
        for (FixAcceptOrder fixOrder : fixOrders) {
            String waybill = fixOrder.getWaybill();
            if(waybill.isEmpty() || waybill == null){
                fixOrder.setWhetherMail(0);
            }else {
                fixOrder.setWhetherMail(1);
            }

        }
        int i = orderSelectMapper.countAcceptOrder(status);
        jsonObject.put("jsonObject",fixOrders);
        jsonObject.put("jsonObjectCount",i);
        return jsonObject;
    }

    /**
     *
     *
     *使用维修员Id查询订单
     *
     *
     * */
    @Override
    public JSONObject selectOrderByFixerId(String id) {
        JSONObject jsonObject = new JSONObject();
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("fixer_id",id);
        List<OrderDetailInfo> orderDetailInfos = orderSelectMapper.selectOrderByFixerId(id);
        for (OrderDetailInfo orderDetailInfo : orderDetailInfos) {
            if (orderDetailInfo.getWaybill().isEmpty()){
                orderDetailInfo.setWhetherMail(0);
            }else {
                orderDetailInfo.setWhetherMail(1);
            }
        }
        Long selectCount = orderSelectMapper.selectCount(orderQueryWrapper);
        jsonObject.put("roleInfo","fixer");
        jsonObject.put("orderCount",selectCount);
        jsonObject.put("order", orderDetailInfos);
        return jsonObject;
    }


    /**
     *
     * 查询维修中与已接单的订单By userId
     *
     * */
    @Override
    public List<OrderStatus> selectIncompleteOrderById(String user_id) {
        List<OrderStatus> orderStatuses = orderSelectMapper.selectIncompleteOrderById(user_id);
        return orderStatuses;
    }


    /**
     *
     * 查询所有维修中与已接单的订单
     *
     * */
    @Override
    public List<OrderStatus> selectIncompleteOrderAll() {
        List<OrderStatus> orderStatuses = orderSelectMapper.selectIncompleteOrderAll();
        return orderStatuses;
    }

    /**
     *
     * 查询用户订单状态
     *
     * */
    @Override
    public List<OrderStatus> userFixOrderStatus(String user_id) {
        List<OrderStatus> orderStatuses = orderSelectMapper.selectUserIdForOrderStatus(user_id);
        return orderStatuses;
    }



    /**
     *
     * 用户查询订单ByUserId
     *
     * */
    public JSONObject selectOrderByUserId(String id) {
        JSONObject jsonObject = new JSONObject();
        List<UserDetailInfo> orderDetailInfos = orderSelectMapper.selectOrderByUserId(id);
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("user_id",id);
        for (UserDetailInfo orderDetailInfo : orderDetailInfos) {
            if (orderDetailInfo.getWaybill().isEmpty()){
                orderDetailInfo.setWhetherMail(0);
            }else {
                orderDetailInfo.setWhetherMail(1);
            }
        }
        Long selectCount = orderSelectMapper.selectCount(orderQueryWrapper);
        jsonObject.put("roleInfo","user");
        jsonObject.put("order", orderDetailInfos);
        jsonObject.put("orderCount",selectCount);
        return jsonObject;
    }

    /**
     *
     * 计算日期查询并查询是否过保修
     *
     * **/
    @Override
    public String guaranteeDate(String order_id) {

        CompleteDate completeDate = orderSelectMapper.selectOrderCompleteDate(order_id);

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(completeDate.getCompleteDate());

        calendar.add(Calendar.DAY_OF_YEAR,180);

        //获取新保修期
        Date time = calendar.getTime();

        SimpleDateFormat Dateformat = new SimpleDateFormat("yyyy-MM-dd");

        String guaranteeDate = Dateformat.format(time);

        return guaranteeDate;
    }



}
