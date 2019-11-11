package com.ylt.servicelogin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

    @RequestMapping("/pageLogin")
    public String login(){
        return "lyear_pages_login";
    }

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

}
