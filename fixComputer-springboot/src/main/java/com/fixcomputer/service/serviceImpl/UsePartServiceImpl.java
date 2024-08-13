package com.fixcomputer.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fixcomputer.domain.entity.UsePartsCount;
import com.fixcomputer.domain.vo.UsePartsInfo;
import com.fixcomputer.mapper.UsePartsMapper;
import com.fixcomputer.service.UsePartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UsePartServiceImpl extends ServiceImpl<UsePartsMapper, UsePartsCount> implements UsePartService {
    /**
     *
     * 添加零件使用信息
     *
     *
     * */
    @Autowired
    UsePartsMapper usePartsMapper;
    @Override
    public boolean addUsePart(UsePartsCount usePartsCount) {
        int insert = usePartsMapper.insert(usePartsCount);

        if(insert==1){
            return true;
        }else{
            return false;
        }

    }




    /**
     *
     *
     * 查询使用信息
     *
     *
     * */
    @Override
    public List<UsePartsInfo> selectUseParts(String order_id) {
        List<UsePartsInfo> usePartsInfos = usePartsMapper.selectUseParts(order_id);
        return usePartsInfos;
    }






}
