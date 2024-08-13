package com.fixcomputer.controller;


import com.alibaba.fastjson2.JSONObject;
import com.fixcomputer.domain.dto.OrderInfo;
import com.fixcomputer.domain.entity.Order;
import com.fixcomputer.domain.vo.CompleteWaybill;
import com.fixcomputer.domain.vo.OrderDetailInfo;
import com.fixcomputer.domain.vo.UserUpdateOrder;
import com.fixcomputer.service.OrderUpdateService;
import com.fixcomputer.utils.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/update_order")
public class UpdateOrderModule {
    @Autowired
    OrderUpdateService orderUpdateService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     *
     *
     * 接收订单更改订单状态
     *
     * */
    @GetMapping("/accept")
    public JSONObject updateStatusOrder(String orderId, HttpServletRequest request){
        String token = request.getHeader("token");
        System.out.println(token);
        Map map = jwtTokenUtil.validateToken(token);
        String fix_id = (String) map.get("id");
        JSONObject jsonObject = orderUpdateService.acceptOrder(orderId, fix_id);
        return jsonObject;
    }

    /**
     *
     * 更新订单为维修状态
     *
     * */
    @PostMapping()
    public boolean updateFixStatus(@RequestBody OrderInfo orderInfo){

        boolean status = orderUpdateService.UpdateFixStatus(orderInfo);

        return status;
    }

    /**
     *
     * 用户修改订单信息
     *
     * */
    @PutMapping()
    public boolean userUpdateFixOrder(@RequestBody UserUpdateOrder userUpdateOrder, HttpServletRequest request){
        String token = request.getHeader("token");
        JwtTokenUtil jwtTokenUtil1 = new JwtTokenUtil();
        Map map = jwtTokenUtil1.validateToken(token);
        String user_id = (String) map.get("id");
        boolean updateOrderInfo = orderUpdateService.userUpdateOrderInfo(userUpdateOrder, user_id);
        return updateOrderInfo;
    }

    /**
     *
     * 取消订单
     *
     * */
    @GetMapping("/{id}")
    public boolean cancelOrder(@PathVariable String id){

        //修改为取消
        boolean orderStatus = orderUpdateService.updateOrderStatus(id, 4);

        return orderStatus;

    }

    /**
     *
     * 删除订单
     *
     * */
    @DeleteMapping("/{order_id}")
    public boolean deleteOrder(@PathVariable String order_id,HttpServletRequest request){
        String token = request.getHeader("token");
        JwtTokenUtil jwtTokenUtil1 = new JwtTokenUtil();
        Map map = jwtTokenUtil1.validateToken(token);
        String id = (String) map.get("id");
        boolean b = orderUpdateService.deleteOrder(order_id, id);
        return b;
    }

    /***
     *
     * 回转到接单中心的订单（转单）
     *
     *
     *
     */

    @GetMapping("/fallback/{order_id}")
    public boolean fallbackOrder(@PathVariable String order_id){

        boolean b = orderUpdateService.fallBackOrder(order_id);
        return b;
    }

    /**
     *
     *添加完成后物流信息
     *
     * */
    @PostMapping("/complete_waybill")
    public boolean complete_waybill(@RequestBody CompleteWaybill waybill){
        boolean b = orderUpdateService.completeWaybill(waybill);
        return b;
    }




}
