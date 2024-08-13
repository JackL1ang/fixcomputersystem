package com.fixcomputer.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fixcomputer.domain.entity.Rate;
import com.fixcomputer.mapper.OrderRateMapper;
import com.fixcomputer.service.OrderRateService;
import com.fixcomputer.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderRateServiceImpl extends ServiceImpl<OrderRateMapper, Rate> implements OrderRateService {

    @Autowired
    OrderRateMapper orderRateMapper;



    /**
     *
     * 创建一个新的订单评价
     *
     * */
    public boolean create_OrderRate(String order_id){
        Rate rate = new Rate();
        rate.setOrderId(order_id);
        rate.setRateStatus(false);
        int insert = orderRateMapper.insert(rate);
        if (insert == 1){
            return true;
        }else {
            return false;
        }


    }

    /***
     *
     *
     * 添加评价信息
     *
     */

    @Override
    public boolean update_OrderRate(Rate rate) {
        DateUtil dateUtil = new DateUtil();
        String dateTime = dateUtil.dateUtilForString();
        rate.setRateStatus(true);
        rate.setCreateTime(dateTime);
        UpdateWrapper<Rate> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("order_id",rate.getOrderId());
        //将评价与分数插入
        int update = orderRateMapper.update(rate, updateWrapper);
        //成功
        if(update == 1){
            return true;
        }else {
            //重新调用新建一个新的评价
            boolean orderRate = create_OrderRate(rate.getOrderId());
            if(orderRate){
                //再次更新
                boolean reUpdate = update_OrderRate(rate);
                if(reUpdate){
                    return true;
                }else {
                    return  false;
                }
            }else {
                return false;
            }
        }

    }


}
