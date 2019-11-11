package com.ylt.servicelogin.interfaceUser;

import com.ylt.servicelogin.userBean.MyUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserService {
    @Select("SELECT id,username,password,permission from test01 WHERE username=#{username}")
    MyUser findOne(String username);
    @Insert("INSERT INTO test01 (id,username,password,permission) VALUES(NULL,?,?,0)")
    int register(String username, String password);
}
