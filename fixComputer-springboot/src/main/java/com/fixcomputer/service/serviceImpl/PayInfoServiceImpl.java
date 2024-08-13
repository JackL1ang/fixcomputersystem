package com.fixcomputer.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fixcomputer.domain.entity.OrderPayInfo;
import com.fixcomputer.mapper.PayInfoMapper;
import com.fixcomputer.service.PayInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayInfoServiceImpl extends ServiceImpl<PayInfoMapper, OrderPayInfo> implements PayInfoService {

    @Autowired
    PayInfoMapper payInfoMapper;



    @Override
    public boolean insertOrderPayInfo(OrderPayInfo orderPayInfo) {
        return false;
    }

    @Override
    public boolean updateOrderPayInfo(String order_id,String create_date) {
        OrderPayInfo orderPayInfo = new OrderPayInfo();
        orderPayInfo.setPaymentDate(create_date);
        orderPayInfo.setStatus(true);
        UpdateWrapper<OrderPayInfo> orderPayInfoUpdateWrapper = new UpdateWrapper<>();

        orderPayInfoUpdateWrapper.eq("order_id",order_id);



        int update = payInfoMapper.update(orderPayInfo, orderPayInfoUpdateWrapper);
        if(update==1){
            return true;
        }else{
            return false;
        }

    }
}
