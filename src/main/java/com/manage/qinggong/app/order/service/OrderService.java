package com.manage.qinggong.app.order.service;

import com.manage.qinggong.app.order.pojo.Order;
import com.manage.qinggong.base.DateUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderService {
    public boolean submitOrder(Order order) {
        //加判断，是否该时间可以预约 并且 该时间段人数未满
        Date orderDate = order.getOrderDate();
        String orderTime = order.getOrderTime();
        String date = DateUtils.dateToStr(orderDate, "yyyy-MM-dd");
        Integer dayOfWeek = DateUtils.dayOfWeek(orderDate);
        //查询开馆时间 place_setup open_time

        //插入记录

        //生成二维码

        return false;
    }
}
