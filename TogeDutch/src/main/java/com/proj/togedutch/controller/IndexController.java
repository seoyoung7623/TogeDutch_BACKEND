package com.proj.togedutch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping(value = "/chathome/1")
    public String main1(){
        return "index";
    }

    @RequestMapping(value = "/chathome/2")
    public String main2(){
        return "chatTest2";
    }
}
