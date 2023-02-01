package com.proj.togedutch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping(value = "/chathome")
    public String main(){
        return "index";
    }
}
