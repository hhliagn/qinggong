package com.manage.qinggong.pc.user.controller;

import com.manage.qinggong.base.pojo.ErrorCode;
import com.manage.qinggong.base.pojo.Response;
import com.manage.qinggong.pc.user.pojo.User;
import com.manage.qinggong.pc.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${user.prop}")
    private String userProp;

    @Autowired
    private UserService userService;

//    @PostMapping(value = "/login")
//    public Response login(User user){
//        if (user == null) return new Response("用户对象为空", ErrorCode.ERROR);
//        String userName = user.getUserName();
//        String password = user.getPassword();
//        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) return new Response("用户名或密码为空", ErrorCode.ERROR);
//        User u = userService.findByName(userName);
//        if (u != null){
//            String password1 = u.getPassword();
//            if (StringUtils.isEmpty(password1)) return new Response("数据库用户密码为空", ErrorCode.ERROR);
//            if (password1.equals(password)) return new Response("登录成功", ErrorCode.SUCCESS, u);
//            return new Response("密码错误", ErrorCode.ERROR);
//        }
//        return new Response("该用户不存在", ErrorCode.ERROR);
//    }

    @PostMapping(value = "/login")
    public Response login(User user) {
        if (user == null) return new Response("用户对象为空", ErrorCode.ERROR);
        String userName = user.getUserName();
        String password = user.getPassword();
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) return new Response("用户名或密码为空", ErrorCode.ERROR);
        return readProp(user);
    }

    /**
     * 通过外部配置文件的方式读取用户信息
     * @param loginUser
     * @return
     */
    private Response readProp(User loginUser) {
        try {
            String userName = loginUser.getUserName();
            String password = loginUser.getPassword();
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(userProp)));
            List<String> users = new ArrayList<>();
            Map<String,String> storeUserMap = new HashMap<>();
            String line = "";
            while ((line = br.readLine()) != null) users.add(line);
            for (String user : users) {
                String[] userArray = user.split(" ");
                String storeUserName = userArray[0];
                String storePassword = userArray[1];
                storeUserMap.put(storeUserName, storePassword);
            }
            if (storeUserMap.containsKey(userName)){
                String storePassword = storeUserMap.get(userName);
                if (StringUtils.isEmpty(storePassword)) return new Response("数据库用户密码为空", ErrorCode.ERROR);
                if (storePassword.equals(password)) return new Response("登录成功", ErrorCode.SUCCESS, loginUser);
            }
            return new Response("该用户不存在", ErrorCode.ERROR);
        } catch (IOException e) {
            e.printStackTrace();
            return new Response("读取用户信息出错", ErrorCode.ERROR);
        }
    }
}
