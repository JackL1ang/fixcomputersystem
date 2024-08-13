package com.fixcomputer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fixcomputer.domain.entity.UsePartsCount;
import com.fixcomputer.domain.vo.UsePartsInfo;

import java.util.List;

public interface UsePartService extends IService<UsePartsCount> {

    boolean addUsePart(UsePartsCount usePartsCount);

    List<UsePartsInfo> selectUseParts(String order_id);
}
