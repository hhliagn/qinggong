package com.manage.qinggong.app.order.controller;

import com.alibaba.fastjson.JSON;
import com.manage.qinggong.app.order.pojo.Order;
import com.manage.qinggong.app.order.service.OrderService;
import com.manage.qinggong.base.pojo.ErrorCode;
import com.manage.qinggong.base.pojo.Response;
import com.manage.qinggong.base.utils.DateUtils;
import com.manage.qinggong.base.utils.QRCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/order")
public class OrderController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/generate")
    public Response generate(@RequestParam("userName") String userName){
        Order order1 = new Order();
        order1.setOrderUserName(userName);
        String jsonString = JSON.toJSONString(order1);
        System.out.println(jsonString);
        String path = null;
        try {
            path = QRCode.encode(jsonString, null);
        } catch (Exception e) {
            return new Response("生成二维码错误", ErrorCode.ERROR);
        }
        return new Response("生成永久二维码成功", ErrorCode.SUCCESS, path);
    }

    /**
     * 二维码只传 order_id
     * 12-13 fix：前端根据返回的order_id生成二维码
     * @param order
     * @return
     */
    @PostMapping(value = "")
    public Response submitOrder(Order order){
        if (order == null) return new Response("预约信息为空", ErrorCode.ERROR);
        Response flag = orderService.submitOrder(order);
        if (flag.getCode() == 200) return new Response("提交预约成功", ErrorCode.SUCCESS, flag.getData());
        return flag;
    }
}
