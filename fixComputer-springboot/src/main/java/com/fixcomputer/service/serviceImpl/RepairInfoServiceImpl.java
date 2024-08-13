package com.fixcomputer.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fixcomputer.domain.entity.RepairInfo;
import com.fixcomputer.mapper.RepairInfoMapper;
import com.fixcomputer.service.RepairInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RepairInfoServiceImpl extends ServiceImpl<RepairInfoMapper, RepairInfo> implements RepairInfoService {

    @Autowired
    RepairInfoMapper repairInfoMapper;



    /**
     * 添加维修信息
     *
     * */
    public boolean addRepairInfo(RepairInfo repairInfo){
        int insert = repairInfoMapper.insert(repairInfo);
        if(insert==1){
            return true;
        }else {
            return false;
        }
    }

    /**
     *
     * 查询维修信息
     *
     * */
    public RepairInfo selectFixInfo(String orderId){
        QueryWrapper<RepairInfo> repairInfoQueryWrapper = new QueryWrapper<>();

        repairInfoQueryWrapper.eq("order_id",orderId);
        RepairInfo repairInfo = repairInfoMapper.selectOne(repairInfoQueryWrapper);
        return repairInfo;
    }

}
