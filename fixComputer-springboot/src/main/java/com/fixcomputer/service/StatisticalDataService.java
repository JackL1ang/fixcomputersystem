package com.fixcomputer.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fixcomputer.domain.entity.Order;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface StatisticalDataService extends IService<Order> {

    List<Map<String, Object>> selectCategoryCountById(String user_id);

    Map<String,Object> SelectCountOrderById(String user_id);


    List<Map<String, Object>> CountWeekOrderById(String user_id);


    List<Map<String, Object>> CountYearOrderById(String user_id);

    JSONObject CountstatisticById(String user_id);

    JSONObject CountstatisticAll();

    JSONObject CountUserOrder(String userId);

}
