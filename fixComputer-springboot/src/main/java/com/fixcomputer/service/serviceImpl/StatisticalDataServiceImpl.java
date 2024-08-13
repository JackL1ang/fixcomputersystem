package com.fixcomputer.service.serviceImpl;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fixcomputer.domain.entity.Order;
import com.fixcomputer.domain.vo.OrderStatus;
import com.fixcomputer.mapper.StatisticalDataMapper;
import com.fixcomputer.service.OrderSelectService;
import com.fixcomputer.service.StatisticalDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StatisticalDataServiceImpl extends ServiceImpl<StatisticalDataMapper, Order> implements StatisticalDataService {

    @Autowired
    StatisticalDataMapper statisticalDataMapper;

    @Autowired
    OrderSelectService orderSelectService;

    /**
     *
     *
     * 查询所有类别
     *
     *
     * */
    public List<Map<String, Object>> selectCategoryCountAll(){
        List<Map<String, Object>> maps = statisticalDataMapper.selectCountCategoryAll();
        return maps;
    }




    /**
     *
     *
     * 统计所有类别By用户ID
     *
     *
     */
    public List<Map<String, Object>> selectCategoryCountById(String user_id) {

        //所有类别
        List<Map<String, Object>> countCategory = statisticalDataMapper.selectCountCategoryById(user_id);

        return countCategory;
    }

    /**
     *
     * 查询所有订单并统计
     *
     * */
    public Map<String, Object> SelectCountOrderAll(){
        HashMap<String, Object> countMap = new HashMap<>();
        double allOrder = statisticalDataMapper.countOrderAll();
        double completeOrder = statisticalDataMapper.countCompleteOrderAll();
        double inCompleteOrder = statisticalDataMapper.countInCompleteOrderAll();

        int complete = (int) (completeOrder / allOrder * 100);
        int incomplete = (int) (inCompleteOrder / allOrder * 100);
        countMap.put("allOrder", allOrder);
        countMap.put("complete", complete);
        countMap.put("incomplete", incomplete);

        return countMap;
    }


    /**
     *
     *
     * 计算订单数值ById
     *
     *
     */
    public Map<String, Object> SelectCountOrderById(String user_id) {
        HashMap<String, Object> countMap = new HashMap<>();
        double allOrder = statisticalDataMapper.countOrderById(user_id);
        double completeOrder = statisticalDataMapper.countCompleteOrderById(user_id);
        double inCompleteOrder = statisticalDataMapper.countInCompleteOrderById(user_id);
        int complete = (int) (completeOrder / allOrder * 100);
        int incomplete = (int) (inCompleteOrder / allOrder * 100);
        countMap.put("allOrder", allOrder);
        countMap.put("complete", complete);
        countMap.put("incomplete", incomplete);
        return countMap;
    }

    /**
     *
     *
     * 统计一周所有订单
     *
     * */
    public List<Map<String, Object>> CountWeekOrderAll() {
        List<Map<String,Object>> allDate = new ArrayList<>();
        //获取当天
        LocalDate today = LocalDate.now();
        //获取七天前
        LocalDate sevenDaysAgo = today.minusDays(7);
        //遍历7天日期 默认值为0
        for(LocalDate date = sevenDaysAgo;!date.isAfter(today);date = date.plusDays(1)){
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("orderDate",date.toString());
            hashMap.put("orderCount",0);
            allDate.add(hashMap);

        }
        //数据库查询结果
        List<Map<String, Object>> countWeekOrderById = statisticalDataMapper.countWeekOrderAll();
        //跟遍历出来的日期对比
        for (Map<String, Object> allDateMap : allDate) {
            for (Map<String, Object> countWeekMap : countWeekOrderById) {
                if(countWeekMap.get("orderDate").toString().equals(allDateMap.get("orderDate").toString())){
                    allDateMap.put("orderCount",countWeekMap.get("orderCount"));
                }
            }
        }


        return allDate;

    }


    /**
     *
     *
     * 统计一周订单ById
     *
     *
     */
    public List<Map<String, Object>> CountWeekOrderById(String user_id) {
        List<Map<String,Object>> allDate = new ArrayList<>();
        //获取当天
        LocalDate today = LocalDate.now();
        //获取七天前
        LocalDate sevenDaysAgo = today.minusDays(7);
        //遍历7天日期 默认值为0
        for(LocalDate date = sevenDaysAgo;!date.isAfter(today);date = date.plusDays(1)){
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("orderDate",date.toString());
            hashMap.put("orderCount",0);
            allDate.add(hashMap);

        }

        //数据库查询结果
        List<Map<String, Object>> countWeekOrderById = statisticalDataMapper.countWeekOrderById(user_id);
        //跟遍历出来的日期对比
        for (Map<String, Object> allDateMap : allDate) {
            for (Map<String, Object> countWeekMap : countWeekOrderById) {
                if(countWeekMap.get("orderDate").toString().equals(allDateMap.get("orderDate").toString())){
                    allDateMap.put("orderCount",countWeekMap.get("orderCount"));
                }
            }
        }


        return allDate;

    }

    /**
     *
     *
     * 统计一年每个月的所有订单量
     *
     *
     */
    public List<Map<String, Object>> CountYearOrderAll() {
        //获取年
        int year = LocalDate.now().getYear();

        //创建年-月格式
        List<Map<String, Object>> allmonths = new ArrayList<>();

        for (int month = 1; month <= 12; month++) {
            HashMap<String, Object> HashMap = new HashMap<>();
            HashMap.put("orderMonth", String.format("%d-%02d", year, month));
            HashMap.put("orderCount", 0);
            allmonths.add(HashMap);
        }

        List<Map<String, Object>> countMonthOrder = statisticalDataMapper.CountYearOrderAll(year);



        for (Map<String, Object> stringObjectMap : countMonthOrder) {

            for (Map<String, Object> allmonth : allmonths) {
                if(allmonth.get("orderMonth").equals(stringObjectMap.get("orderMonth"))){
                    allmonth.put("orderCount",stringObjectMap.get("orderCount"));
                }
            }
        }

        return allmonths;


    }



    /**
     *
     *
     * 统计一年每个月的订单量ById
     *
     *
     */
    public List<Map<String, Object>> CountYearOrderById(String user_id) {
        //获取年
        int year = LocalDate.now().getYear();

        //创建年-月格式
        List<Map<String, Object>> allmonths = new ArrayList<>();

        for (int month = 1; month <= 12; month++) {
            HashMap<String, Object> HashMap = new HashMap<>();
            HashMap.put("orderMonth", String.format("%d-%02d", year, month));
            HashMap.put("orderCount", 0);
            allmonths.add(HashMap);
        }

        List<Map<String, Object>> countMonthOrder = statisticalDataMapper.CountYearOrderById(user_id, year);


        for (Map<String, Object> stringObjectMap : countMonthOrder) {

            for (Map<String, Object> allmonth : allmonths) {
                if(allmonth.get("orderMonth").equals(stringObjectMap.get("orderMonth"))){
                    allmonth.put("orderCount",stringObjectMap.get("orderCount"));
                }
            }
        }

       return allmonths;


}





    /**
     *
     * 将统计数据整合成
     *For Fixer
     *
     */
    public JSONObject CountstatisticById(String user_id){
        //统计的订单
        Map<String, Object> selectedCountOrderById = SelectCountOrderById(user_id);
        JSONObject json = (JSONObject) JSON.toJSON(selectedCountOrderById);
        //类别
        List<Map<String, Object>> countCategory = selectCategoryCountById(user_id);
        json.put("category",countCategory);
        //每日订单量
        List<Map<String, Object>> countedWeekOrder = CountWeekOrderById(user_id);
        json.put("weekOrder",countedWeekOrder);
        //每个月的订单
        List<Map<String, Object>> countMonthOrderById = CountYearOrderById(user_id);
        json.put("yearOrder",countMonthOrderById);
        //列出未完成订单
        List<OrderStatus> orderStatuses = orderSelectService.selectIncompleteOrderById(user_id);
        json.put("statusorder",orderStatuses);

        return json;

    }



    /**
     *
     *
     * 整合所有信息for ADMIN
     *
     *
     * */
    public JSONObject CountstatisticAll(){

        //统计的订单
        Map<String, Object> selectedCountOrderAll = SelectCountOrderAll();
        JSONObject json = (JSONObject) JSON.toJSON(selectedCountOrderAll);

        //类别
        List<Map<String, Object>> countCategory = selectCategoryCountAll();
        json.put("category",countCategory);

        //每日订单量
        List<Map<String, Object>> countedWeekOrder = CountWeekOrderAll();
        json.put("weekOrder",countedWeekOrder);

        //每月订单
        List<Map<String, Object>> countMonthOrderById = CountYearOrderAll();
        json.put("yearOrder",countMonthOrderById);

        //列出未完成订单所有订单
        List<OrderStatus> orderStatuses = orderSelectService.selectIncompleteOrderAll();
        json.put("statusorder",orderStatuses);


        return json;

    }


    /**
     *
     * 整合完成订单、未完成、全部订单数量
     *
     * */
    public JSONObject CountUserOrder(String userId){

        JSONObject jsonObject = new JSONObject();

        double allOrder = statisticalDataMapper.countOrderByUserId(userId);

        double completeOrder = statisticalDataMapper.countCompleteOrderByUserId(userId);

        double inCompleteOrder = statisticalDataMapper.countInCompleteOrderByUserId(userId);

        jsonObject.put("allOrder",allOrder);

        jsonObject.put("completeOrder",completeOrder);

        jsonObject.put("inCompleteOrder",inCompleteOrder);

        return jsonObject;
    }



}