package com.fixcomputer.service.serviceImpl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fixcomputer.domain.dto.FixInfo;
import com.fixcomputer.domain.entity.*;
import com.fixcomputer.domain.vo.AllOrderInfo;
import com.fixcomputer.domain.vo.OrderDetailInfo;
import com.fixcomputer.domain.vo.UsePartsInfo;
import com.fixcomputer.mapper.*;
import com.fixcomputer.service.*;
import com.fixcomputer.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class FixInfoServiceImpl implements FixInfoService {


    @Autowired
    RepairInfoService repairInfoService;

    @Autowired
    UsePartService usePartService;

    @Autowired
    PartsService partsService;

    @Autowired
    OrderUpdateService orderUpdateService;

    @Autowired
    OrderSelectMapper orderSelectMapper;

    @Autowired
    RepairInfoMapper repairInfoMapper;

    @Autowired
    PartsMapper partsMapper;

    @Autowired
    UsePartsMapper usePartsMapper;

    @Autowired
    PayInfoMapper payInfoMapper;







    /**
     *
     *
     * 操作：添加维修信息、修改零件库存和修改订单状态
     *
     *
     * */
    @Override
    @Transactional
    public JSONObject createFixInfo(FixInfo fixInfo) {
        DateUtil dateUtil = new DateUtil();

        JSONObject jsonStatus = new JSONObject();

        //从传输过来的信息分离
        RepairInfo repairInfo = BeanUtil.copyProperties(fixInfo, RepairInfo.class);
        repairInfo.setCreateDate(dateUtil.dateUtilForString());

        //成功维修
        if(fixInfo.isWhether_fix()){
            try {

                //repairInfoService存入信息
                repairInfoService.addRepairInfo(repairInfo);
                fixInfo.getParts().forEach(PartsCount -> {
                    UsePartsCount usePartsCount = BeanUtil.copyProperties(PartsCount, UsePartsCount.class);
                    usePartsCount.setOrder_id(fixInfo.getOrder_id());
                    //零件使用信息添加
                    usePartService.addUsePart(usePartsCount);

                    //减去备件库存
                    partsService.subStock(usePartsCount.getPartsId(), usePartsCount.getAmount());
                });
                //更新订单状态
                orderUpdateService.updateOrderStatus(fixInfo.getOrder_id(), 0);

                //创建支付状态与金额
                OrderPayInfo orderPayInfo = new OrderPayInfo();
                orderPayInfo.setOrderId(fixInfo.getOrder_id());
                orderPayInfo.setStatus(false);
                orderPayInfo.setTotalAmount(fixInfo.getAmount());
                payInfoMapper.insert(orderPayInfo);
                jsonStatus.put("status",true);
                return jsonStatus;
            }catch (Exception exception){
                //事务失败后 回滚
                log.warn(exception.toString());
                jsonStatus.put("status",false);
                return jsonStatus;

            }
        }else {
            //维修失败
            try{
                //repairInfoService存入信息
                repairInfoService.addRepairInfo(repairInfo);
                //更新订单状态
                orderUpdateService.updateOrderStatus(fixInfo.getOrder_id(), 0);
                jsonStatus.put("status",true);
                return jsonStatus;
            }catch (Exception e){
                //事务失败后 回滚
                log.warn(e.toString());
                jsonStatus.put("status",false);
                return jsonStatus;
            }
        }
    }

    /***
     *
     * 查询订单与维修信息
     *
     */
    public JSONObject selectOrderFixInfo(String orderId){
        JSONObject jsonObject = new JSONObject();
        //查询订单信息
        OrderDetailInfo orderDetailInfo = orderSelectMapper.selectByOrderId(orderId);
        //查询维修信息
        RepairInfo repairInfo = repairInfoService.selectFixInfo(orderId);
        //查询维修零件信息
        List<UsePartsInfo> usePartsInfos = usePartService.selectUseParts(orderId);

        jsonObject.put("orderDetailInfo",orderDetailInfo);

        jsonObject.put("repairInfo",repairInfo);

        jsonObject.put("usePartsInfo", usePartsInfos);

        return jsonObject;

    }

    @Transactional
    public boolean modifyOrderInfo(AllOrderInfo allOrderInfo){
        try {

            //修改订单信息
            QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
            orderQueryWrapper.eq("order_id",allOrderInfo.getOrderDetailInfo().getOrderId());
            Order orderMapper = orderSelectMapper.selectOne(orderQueryWrapper);
            UpdateWrapper<Order> orderUpdateWrapper = new UpdateWrapper<>();
            orderUpdateWrapper.eq("order_id", allOrderInfo.getOrderDetailInfo().getOrderId());
            BeanUtil.copyProperties(allOrderInfo.getOrderDetailInfo(), orderMapper);
            orderMapper.setAccept_date(null);
            orderMapper.setStartTime(null);
            orderSelectMapper.update(orderMapper, orderUpdateWrapper);

            if (allOrderInfo.getRepairInfo() !=null) {
                //修改维修信息
                UpdateWrapper<RepairInfo> repairInfoUpdateWrapper = new UpdateWrapper<>();
                repairInfoUpdateWrapper.eq("id", allOrderInfo.getRepairInfo().getId());
                repairInfoMapper.update(allOrderInfo.getRepairInfo(), repairInfoUpdateWrapper);

                //修改零件信息
                allOrderInfo.getUsePartsInfo().forEach(usePartsInfo -> {

                    //查询原本的使用量
                    UsePartsCount usePartsCount = usePartsMapper.selectById(usePartsInfo.getUseParts_id());
                    Integer amount = usePartsCount.getAmount() - usePartsInfo.getAmount();

                    //如果相差数量为0 不进行操作
                    if (amount != 0) {
                        //更新使用的数量
                        int i = partsMapper.updatePartsStock(amount, usePartsInfo.getParts_id());
                    }
                    //更新使用零件数据
                    UsePartsCount nowUsePartsCount = new UsePartsCount();
                    nowUsePartsCount.setAmount(usePartsInfo.getAmount());
                    QueryWrapper<UsePartsCount> usePartsCountQueryWrapper = new QueryWrapper<>();
                    usePartsCountQueryWrapper.eq("id", usePartsInfo.getUseParts_id());
                    usePartsMapper.update(nowUsePartsCount, usePartsCountQueryWrapper);
                });
            }
            return true;
        }catch (Exception e){
            log.warn(e.toString());
            return false;
        }

    }
}
