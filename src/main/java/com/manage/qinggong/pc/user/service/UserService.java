package com.manage.qinggong.pc.user.service;

import com.manage.qinggong.pc.user.mapper.UserMapper;
import com.manage.qinggong.pc.user.pojo.User;
import com.manage.qinggong.pc.user.pojo.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User findById(Long userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    public User findByName(String userName) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUserNameEqualTo(userName);
        List<User> users = userMapper.selectByExample(example);
        if (users != null && users.size() > 0) return users.get(0);
        return null;
    }
}
