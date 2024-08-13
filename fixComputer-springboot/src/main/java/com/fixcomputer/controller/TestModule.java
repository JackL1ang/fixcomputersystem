package com.fixcomputer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestModule {

    @GetMapping("/test")
    public String test(){
        return "redirect:https://www.baidu.com";
    }
}
