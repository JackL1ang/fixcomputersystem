package com.fixcomputer.controller;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSONObject;
import com.fixcomputer.domain.dto.FixInfo;
import com.fixcomputer.domain.entity.RepairInfo;
import com.fixcomputer.domain.entity.UsePartsCount;
import com.fixcomputer.domain.vo.AllOrderInfo;
import com.fixcomputer.service.*;
import com.fixcomputer.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fix_info")
public class FixInfoModule {


    @Autowired
    FixInfoService fixInfoService;




    /**
     *
     * 新建维修信息
     *
     * */
    @PostMapping
    public JSONObject create_FixInfo(@RequestBody FixInfo fixInfo){

        JSONObject fixInfoServiceFixInfo = fixInfoService.createFixInfo(fixInfo);

        return fixInfoServiceFixInfo;
    }


    /***
     *
     * 用订单Id查询所有维修信息和订单信息
     *
     *
     */
    @GetMapping
    public JSONObject selectAllOrderFixInfo(String orderId){
        JSONObject jsonObject = fixInfoService.selectOrderFixInfo(orderId);
        return jsonObject;
    }

    @PutMapping
    public boolean modifyFixInfo(@RequestBody AllOrderInfo allOrderInfo){
        boolean modifyOrderInfo = fixInfoService.modifyOrderInfo(allOrderInfo);

        return modifyOrderInfo;

    }

}
