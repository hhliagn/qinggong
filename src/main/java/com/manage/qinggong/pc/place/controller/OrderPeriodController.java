package com.manage.qinggong.pc.place.controller;

import com.manage.qinggong.base.Response;
import com.manage.qinggong.pc.place.pojo.OrderPeriod;
import com.manage.qinggong.pc.place.service.OrderPeriodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orderPeriod")
public class OrderPeriodController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderPeriodService orderPeriodService;

    @PostMapping(value = "")
    public Response all(){
        List<OrderPeriod> data = orderPeriodService.all();
        return new Response("查询成功", data);
    }

    @PostMapping(value = "/add")
    public Response add(OrderPeriod orderPeriod){
        if (orderPeriod == null) return new Response("对象为空");
        Long limit = orderPeriod.getLimit();
        if (limit < 0) return new Response("人数限制不能小于 0");
        boolean flag = orderPeriodService.add(orderPeriod);
        if (flag) return new Response("添加成功");
        return new Response("添加失败");
    }


}
