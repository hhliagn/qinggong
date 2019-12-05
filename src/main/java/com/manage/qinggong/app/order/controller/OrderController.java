package com.manage.qinggong.app.order.controller;

import com.manage.qinggong.app.order.pojo.Order;
import com.manage.qinggong.base.DateUtils;
import com.manage.qinggong.base.Response;
import com.manage.qinggong.app.order.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/order")
public class OrderController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderService orderService;

    @PostMapping(value = "")
    public Response submitOrder(@RequestBody Order order){
        if (order == null) return new Response("预约信息为空");
        //加判断，是否该时间可以预约 并且 该时间段人数未满
        Date orderDate = order.getOrderDate();
        String orderTime = order.getOrderTime();
        String date = DateUtils.dateToStr(orderDate, "yyyy-MM-dd");
        Integer dayOfWeek = DateUtils.dayOfWeek(orderDate);
        //查询开馆时间 place_setup open_time

        //插入记录

        //生成二维码
        return null;
    }
}
