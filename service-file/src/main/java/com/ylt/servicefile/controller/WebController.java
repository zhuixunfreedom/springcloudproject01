package com.ylt.servicefile.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

    @RequestMapping("/page")
    public String page(){
        return "lyear_forms_updown";
    }

}
