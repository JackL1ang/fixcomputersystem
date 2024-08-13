package com.fixcomputer.controller;


import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fixcomputer.domain.dto.PartsSearchPage;
import com.fixcomputer.domain.entity.Parts;
import com.fixcomputer.service.PartsService;
import com.fixcomputer.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/parts")
public class PartsModule {
    @Autowired
    private PartsService partsService;


    /**
     *
     * 添加备件
     *
     * */
    @PostMapping("/add")
    public boolean addParts(@RequestBody Parts parts){
        UUID uuid = UUID.randomUUID();
        parts.setPartsId(uuid.toString());
        DateUtil dateUtil = new DateUtil();
        String date = dateUtil.dateUtilForString();
        parts.setCreateDate(date);
        boolean b = partsService.addParts(parts);
        return b;
    }


    /**
     *
     * 查询所有备件(分页）
     *
     * */
    @GetMapping("/page/{pages}")
    public Page<Parts> selectAllParts(@PathVariable int pages){
        Page<Parts> parts = partsService.selectAllPartsByPage(pages);
        return parts;
    }

    /**
     *
     * 使用id查询备件信息
     *
     * */
    @GetMapping("/{partsId}")
    public Parts selectPartsForId(@PathVariable String partsId){
        Parts parts = partsService.selectForId(partsId);
        return parts;
    }



    /**
     *
     * 修改零件信息
     *
     * */
    @PostMapping
    public boolean updateParts(@RequestBody Parts parts){
        boolean states = partsService.updateParts(parts);
        return states;
    }

    /**
     *
     * 搜索零件名+分页
     *
     * */
    @PostMapping("/search")
    public Page<Parts> searchPartName(@RequestBody PartsSearchPage page){
        Page<Parts> partsPage = partsService.searchPartchName(page.getName(), page.getPage());
        System.out.println(partsPage);
        return partsPage;
    }

    @GetMapping
    public List<Parts> selectAllParts(){
        List<Parts> parts = partsService.selectAllParts();
        return parts;
    }


}
