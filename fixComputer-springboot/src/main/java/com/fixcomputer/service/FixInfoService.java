package com.fixcomputer.service;

import com.alibaba.fastjson2.JSONObject;
import com.fixcomputer.domain.dto.FixInfo;
import com.fixcomputer.domain.vo.AllOrderInfo;

public interface FixInfoService {

    JSONObject createFixInfo(FixInfo fixInfo);

    JSONObject selectOrderFixInfo(String orderId);

    boolean modifyOrderInfo(AllOrderInfo allOrderInfo);
}
