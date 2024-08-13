package com.fixcomputer.domain.vo;


import com.fixcomputer.domain.entity.RepairInfo;
import lombok.Data;

import java.util.List;

@Data
public class AllOrderInfo {

    private  OrderDetailInfo orderDetailInfo;

    private List<UsePartsInfo> usePartsInfo;

    private RepairInfo repairInfo;

}
