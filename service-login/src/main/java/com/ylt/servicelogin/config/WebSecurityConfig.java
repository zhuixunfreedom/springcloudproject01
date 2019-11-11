package com.ylt.servicelogin.config;

import com.ylt.servicelogin.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {

        //        自定义页面访问权限
        http.authorizeRequests()
                .antMatchers("/pageLogin").permitAll()//默认登录无需权限可访问
                .antMatchers("/reg").permitAll()//注册页面无需权限可访问
//                .antMatchers("/lyear/**").hasRole("L1")//访问lyear文件夹以及内部所有，需要L1权限
                .antMatchers("/index").permitAll();//访问首页，需要L1权限


        //        自定义登录页面以及相关配置
        http.formLogin()
                .usernameParameter("username").passwordParameter("password")//设置绑定的参数
                .loginPage("/pageLogin")//登录页面
                .loginProcessingUrl("/login")//配置登录请求路径
                .defaultSuccessUrl("/index")//登录成功跳转
                .failureUrl("/pageLogin");//登录失败跳转

        //        开启记住我功能，即自动添加cookie给对方
        http.rememberMe()
                .rememberMeParameter("rememberMe")//设置绑定的参数
                .tokenRepository(persistentTokenRepository())//去数据库验证cookies
                .tokenValiditySeconds(600);//设置cookie保存时间 单位秒;

        //        自定义注销功能返回页面,清空cookie
        http.logout().logoutSuccessUrl("/pageLogin");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //添加默认可以登录的用户名以及密码
//        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("admin").password(new BCryptPasswordEncoder().encode("admin")).roles("L1","L2","L3")
//                .and().passwordEncoder(new BCryptPasswordEncoder()).withUser("123").password(new BCryptPasswordEncoder().encode("123")).roles("L1")
//                .and().passwordEncoder(new BCryptPasswordEncoder()).withUser("杨凌涛").password(new BCryptPasswordEncoder().encode("123456")).roles("L1","L2");
        //配置数据库中的用户名及密码及角色
        auth.userDetailsService(userDetailsServiceImpl()).passwordEncoder(bCryptPasswordEncoder());

    }



    @Override
    public void configure(WebSecurity web){
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers("/*.html","/css/**","/fonts/**","/images/**","/js/**");
    }

    /**
     * 用来做remember me的处理
     * 校验cookie
     */
    @Autowired
    private DataSource dataSource;
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        // 如果token表不存在，使用下面语句可以初始化该表；若存在，请注释掉这条语句，否则会报错。
//         tokenRepository.setCreateTableOnStartup(true);
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }

//    加密
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
//    获取用户
    @Bean
    public UserDetailsServiceImpl userDetailsServiceImpl() {
        return new UserDetailsServiceImpl();
    }
}
