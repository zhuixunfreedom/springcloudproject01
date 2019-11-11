package com.ylt.servicelogin.controller;

import com.ylt.servicelogin.interfaceUser.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RegisterController {
    private UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    public void setBCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @RequestMapping("/reg")
    public String register(String username,String password){
        if(userService.findOne(username)!=null){
            return "{message:用户名已存在}";
        }
        int register = userService.register(username, bCryptPasswordEncoder.encode(password));
        if(register==0){
            return "{message:注册失败}";
        }else{
            return "{message:注册成功}";
        }
    }
}
