package com.ylt.servicelogin.impl;


import com.ylt.servicelogin.interfaceUser.UserService;
import com.ylt.servicelogin.userBean.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsServiceImpl implements UserDetailsService {

    private UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //查询用户
        MyUser user = userService.findOne(username);
        //构建角色
        List<GrantedAuthority> authorities=new ArrayList<>();
        //添加权限
//        authorities.add(new SimpleGrantedAuthority("ROLE_L1"));

        System.out.println(user);
        if (user!=null) {
            for(int i = 0;i <= user.getPermission();i++){
                authorities.add(new SimpleGrantedAuthority("ROLE_L"+i));
            }
            /*
            * 若数据库密码为明文需加密,才能通过检测
            * 通过bCryptPasswordEncoder.encode(password)
            * */
            return new User(username,user.getPassword(), authorities);
        }else{
            return null;
        }
    }

}
