package com.fixcomputer.service.serviceImpl;


import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fixcomputer.domain.entity.Parts;
import com.fixcomputer.mapper.PartsMapper;
import com.fixcomputer.service.PartsService;
import jakarta.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PartsServiceImpl extends ServiceImpl<PartsMapper,Parts> implements PartsService {

    @Autowired
    PartsMapper partsMapper;

    /**
     *
     * 添加备件
     *
     * */
    @Override
    public boolean addParts(Parts parts) {
        int insert = partsMapper.insert(parts);
        if(insert ==1 ){
            return  true;
        }else {
            return false;
        }
    }

    /**
     *
     * 查询所有备件信息
     *
     * */
    @Override
    public Page<Parts> selectAllPartsByPage(int pages) {

        Page<Parts> page = new Page<>(pages, 9);
        Page<Parts> parts = partsMapper.selectPage(page, null);

        return parts;
    }

    /**
     *
     * 使用id查找备件信息
     * */
    @Override
    public Parts selectForId(String partsId) {
        Parts parts = partsMapper.selectById(partsId);
        return parts;
    }

    /**
     *
     * 修改备件信息
     *
     * */
    @Override
    public boolean updateParts(Parts parts) {
        UpdateWrapper<Parts> partsUpdateWrapper = new UpdateWrapper<>();
        partsUpdateWrapper.eq("parts_id",parts.getPartsId());
        int update = partsMapper.update(parts, partsUpdateWrapper);
        if(update ==1){
            return true;
        }else {
            return false;
        }
    }

    /**
     *
     * 减去库存
     *
     *
     * */
    public boolean subStock(String parts_id,int amount){
        int i = partsMapper.subStock(parts_id, amount);
        if(i==1){
            return true;
        }else{
            return false;
        }

    }

    /**
     *
     * 搜索零件名
     *
     * */
    public Page<Parts> searchPartchName(String name,int page){
        LambdaQueryWrapper<Parts> partsLambdaQueryWrapper = new LambdaQueryWrapper<>();
        partsLambdaQueryWrapper.like(Parts::getName,name);

        Page<Parts> pages = new Page<>(page, 9);
        Page<Parts> partsPage = partsMapper.selectPage(pages, partsLambdaQueryWrapper);


        return partsPage;
    }

    @Override
    public List<Parts> selectAllParts() {
        List<Parts> parts = partsMapper.selectList(new QueryWrapper<>());
        return parts;
    }

}
