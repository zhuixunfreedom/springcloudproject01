package com.ylt.serviceorder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

    @RequestMapping("/page")
    public String page(){
        return "lyear_pages_doc";
    }

}
