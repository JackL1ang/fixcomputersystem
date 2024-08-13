package com.fixcomputer.controller;

import com.fixcomputer.domain.entity.Rate;
import com.fixcomputer.service.OrderRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rate")
public class OrderRateModule {

    @Autowired
    OrderRateService rateService;

    @PostMapping
    public boolean rateOrder(@RequestBody Rate rate){
        boolean rateBoolean = rateService.update_OrderRate(rate);
        return rateBoolean;

    }
}
