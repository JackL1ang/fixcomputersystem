package com.fixcomputer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fixcomputer.domain.entity.UsePartsCount;
import com.fixcomputer.domain.vo.UsePartsInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UsePartsMapper extends BaseMapper<UsePartsCount> {
    List<UsePartsInfo> selectUseParts(String order_id);
}
