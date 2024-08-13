package com.fixcomputer.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fixcomputer.domain.entity.Parts;

import java.util.List;

public interface PartsService extends IService<Parts> {
    boolean addParts(Parts parts);

    Page<Parts> selectAllPartsByPage(int pages);

    Parts selectForId(String partsId);

    boolean updateParts(Parts parts);

    boolean subStock(String parts_id,int amount);

    Page<Parts> searchPartchName(String name ,int page);


    List<Parts> selectAllParts();



}
