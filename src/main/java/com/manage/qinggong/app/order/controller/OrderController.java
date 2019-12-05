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
        boolean flag = orderService.submitOrder(order);
        if (flag) return new Response("提交预约成功", flag);
        return new Response("提交预约失败", flag);
    }
}
