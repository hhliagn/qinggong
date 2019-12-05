package com.manage.qinggong.pc.user.controller;

import com.manage.qinggong.base.Response;
import com.manage.qinggong.pc.user.pojo.User;
import com.manage.qinggong.pc.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @PostMapping(value = "/login")
    public Response login(User user){
        if (user == null) return new Response("用户对象为空");
        String userName = user.getUserName();
        String password = user.getPassword();
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) return new Response("用户名或密码为空");
        User u = userService.findByName(userName);
        if (u != null){
            String password1 = u.getPassword();
            if (StringUtils.isEmpty(password1)) return new Response("数据库用户密码为空");
            if (password1.equals(password)) return new Response("登录成功",u);
            return new Response("密码错误");
        }
        return new Response("该用户不存在");
    }


}
