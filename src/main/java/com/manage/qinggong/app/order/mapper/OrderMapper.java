package com.manage.qinggong.app.order.mapper;

import com.manage.qinggong.app.order.pojo.Order;
import com.manage.qinggong.app.order.pojo.OrderExample;
import java.util.List;
import my.mybatis.base.BaseMapper;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper extends BaseMapper<Order, OrderExample, Integer> {

    Integer insertAndGet(Order order) ;
}