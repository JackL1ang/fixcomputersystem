package com.fixcomputer.controller;


import com.alibaba.fastjson2.JSONObject;
import com.fixcomputer.domain.entity.Order;
import com.fixcomputer.domain.vo.FixAcceptOrder;
import com.fixcomputer.domain.vo.OrderDetailInfo;
import com.fixcomputer.domain.vo.OrderStatus;
import com.fixcomputer.service.OrderSelectService;
import com.fixcomputer.utils.DateUtil;
import com.fixcomputer.utils.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/order")
public class SelectOrderModule {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private OrderSelectService orderSelectServiceImpl;

    @Transactional
    @PostMapping("/add")
    public boolean CreateOrder(HttpServletRequest request,@RequestBody Order order){
        DateUtil dateUtil = new DateUtil();
        Date time = dateUtil.dateUtilForDate();

        //当前时间
        String format = dateUtil.dateUtilForString();
        Random random = new Random();
        //随机生成一个4位数
        int i = random.nextInt(10000);
        //将时间与随机生成为订单号
        String order_id = format+i;
        order.setOrder_id(order_id);
        String token = request.getHeader("token");
        Map map = jwtTokenUtil.validateToken(token);
        //获取用户id
        String user_id = (String) map.get("id");


        //设置用户id
        order.setUser_id(user_id);
        //设置订单的开始时间
        order.setStartTime(time);
        //设置订单状态
        order.setStatus(1);
        boolean addStatus = orderSelectServiceImpl.addOrder(order);

        return addStatus;
    }

    /**
     *
     * 使用订单号查询
     *
     * */
    @GetMapping
    public OrderDetailInfo selectOrderById(String orderId, HttpServletRequest request){

        OrderDetailInfo orderDetailInfo = orderSelectServiceImpl.selectByOrderId(orderId);

        return orderDetailInfo;
    }


    /**
     *
     *查询用户的所有订单
     *
     * */
    @GetMapping("/all")
    public JSONObject selectAllOrder(HttpServletRequest request){
        JSONObject jsonStatus = new JSONObject();
        String token = request.getHeader("token");
        //判断token是否空
        if(token == null || token.isEmpty() || token.equals("undefined")){
            jsonStatus.put("status",false);
            return jsonStatus;
        }else {
            Map map = jwtTokenUtil.validateToken(token);
            //从token获取用户角色
            String role = (String) map.get("role");
            String id = (String) map.get("id");
            //如果是管理员
            if(role.equals("admin")){
                JSONObject jsonInfo = orderSelectServiceImpl.selectAllOrder();
                return jsonInfo;

            }else if(role.equals("fixer")){
                //维修员
                JSONObject jsonInfo = orderSelectServiceImpl.selectOrderByFixerId(id);
                return jsonInfo;

            }else {
                //用户
                JSONObject jsonInfo = orderSelectServiceImpl.selectOrderByUserId(id);
                return jsonInfo;
            }
        }



    }
    //查询已下单的订单
    @GetMapping("/fixorder")
    public JSONObject fixOrder(){
        //查询状态为1的订单 1：为已下单状态
        JSONObject jsonObject = orderSelectServiceImpl.selectOrderStatus(1);

        return jsonObject;
    }

    /**
     *
     * 查询用户所有订单状态
     *
     * */
    @GetMapping("/status")
    public List<OrderStatus> userFixOrderStatus(HttpServletRequest request){
        String token = request.getHeader("token");

        JwtTokenUtil jwtTokenUtil1 = new JwtTokenUtil();
        Map map = jwtTokenUtil1.validateToken(token);
        String user_id = (String) map.get("id");

        List<OrderStatus> orderStatuses = orderSelectServiceImpl.userFixOrderStatus(user_id);
        return orderStatuses;
    }



}
