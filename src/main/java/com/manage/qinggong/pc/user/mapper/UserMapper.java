package com.manage.qinggong.pc.user.mapper;

import com.manage.qinggong.pc.user.pojo.User;
import com.manage.qinggong.pc.user.pojo.UserExample;
import java.util.List;
import my.mybatis.base.BaseMapper;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends BaseMapper<User, UserExample, Long> {
}