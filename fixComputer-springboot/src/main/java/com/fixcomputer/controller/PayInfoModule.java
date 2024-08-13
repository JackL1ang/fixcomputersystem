package com.fixcomputer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController("/alipay")
public class PayInfoModule {

    @GetMapping
    public void allOrderIsPay(){

    }
}
