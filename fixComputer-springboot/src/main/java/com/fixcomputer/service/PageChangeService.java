package com.fixcomputer.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fixcomputer.domain.vo.OrderDetailInfo;
import com.fixcomputer.domain.vo.UserDetailInfo;

import java.util.List;

public interface PageChangeService {


    List<OrderDetailInfo> orderPageChangeForAdmin(int page);

    List<OrderDetailInfo> orderPageChangeForFixer(int page,String id);

    List<UserDetailInfo> orderPageChangeForUser(int page,String id);

    JSONObject fixAcceptOrderPage(int pages, int status);



}
