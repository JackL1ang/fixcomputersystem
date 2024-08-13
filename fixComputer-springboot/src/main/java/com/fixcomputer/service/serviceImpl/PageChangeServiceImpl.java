package com.fixcomputer.service.serviceImpl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fixcomputer.domain.vo.FixAcceptOrder;
import com.fixcomputer.domain.vo.OrderDetailInfo;
import com.fixcomputer.domain.vo.UserDetailInfo;
import com.fixcomputer.mapper.OrderSelectMapper;
import com.fixcomputer.mapper.PageChangeMapper;
import com.fixcomputer.service.PageChangeService;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
public class PageChangeServiceImpl implements PageChangeService {

    @Autowired
    PageChangeMapper changeMapper;

    @Autowired
    OrderSelectMapper orderSelectMapper;

    /**
     *
     * 管理员分页
     *
     * */
    @Override
    public List<OrderDetailInfo> orderPageChangeForAdmin(int page) {
        //一页显示的数量
        int countPage = (page-1) * 9;
        List<OrderDetailInfo> orderDetailInfos = changeMapper.orderPageChangeForAdmin(countPage);

        return orderDetailInfos;

    }

    /**
     *
     * 维修员分页
     *
     * */
    @Override
    public List<OrderDetailInfo> orderPageChangeForFixer(int page,String id) {
        int countPage = (page-1) * 9;
        List<OrderDetailInfo> orderDetailInfos = changeMapper.orderPageChangeForFixer(countPage,id);
        for (OrderDetailInfo orderDetailInfo : orderDetailInfos) {
            if (orderDetailInfo.getWaybill().isEmpty()){
                orderDetailInfo.setWhetherMail(0);
            }else {
                orderDetailInfo.setWhetherMail(1);
            }
        }
        return orderDetailInfos;
    }

    /**
     *
     * 用户分页
     *
     * */
    @Override
    public List<UserDetailInfo> orderPageChangeForUser(int page,String id) {
        int countPage = (page-1) * 9;
        List<UserDetailInfo> orderDetailInfos = changeMapper.orderPageChangeForUser(countPage,id);
        for (UserDetailInfo orderDetailInfo : orderDetailInfos) {
            if (orderDetailInfo.getWaybill().isEmpty()){
                orderDetailInfo.setWhetherMail(0);
            }else {
                orderDetailInfo.setWhetherMail(1);
            }
        }
        return orderDetailInfos;
    }

    public JSONObject fixAcceptOrderPage(int pages, int status){
        int countPage = (pages-1) * 6;
        List<FixAcceptOrder> fixAcceptOrders = changeMapper.acceptOrderPage(countPage, status);
        JSONObject jsonObject = new JSONObject();
        for (FixAcceptOrder fixAcceptOrder : fixAcceptOrders) {
            String waybill = fixAcceptOrder.getWaybill();
            if(waybill.isEmpty() || waybill == null){
                fixAcceptOrder.setWhetherMail(0);
            }else {
                fixAcceptOrder.setWhetherMail(1);
            }
        }
        int i = orderSelectMapper.countAcceptOrder(status);
        jsonObject.put("jsonObject",fixAcceptOrders);
        jsonObject.put("jsonObjectCount",i);
        return jsonObject;
    }
}
