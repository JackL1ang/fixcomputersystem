package com.fixcomputer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fixcomputer.domain.entity.RepairInfo;

public interface RepairInfoService extends IService<RepairInfo> {
    boolean addRepairInfo(RepairInfo repairInfo);

    RepairInfo selectFixInfo (String order_id);
}
