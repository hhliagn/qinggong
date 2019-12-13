package com.manage.qinggong.pc.place.controller;

import com.manage.qinggong.base.pojo.ErrorCode;
import com.manage.qinggong.base.pojo.Response;
import com.manage.qinggong.pc.place.mapper.PlaceSetupMapper;
import com.manage.qinggong.pc.place.pojo.OrderPeriod;
import com.manage.qinggong.pc.place.pojo.PlaceSetup;
import com.manage.qinggong.pc.place.service.OrderPeriodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderPeriod")
public class OrderPeriodController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderPeriodService orderPeriodService;

    @GetMapping(value = "")
    public Response all(){
        List<OrderPeriod> data = orderPeriodService.all();
        return new Response("查询成功", ErrorCode.SUCCESS, data);
    }

    @PostMapping(value = "/submit")
    public Response submit(@RequestBody List<OrderPeriod> orderPeriods){
        if (CollectionUtils.isEmpty(orderPeriods)) return new Response("记录集合为空", ErrorCode.ERROR);
        return orderPeriodService.submit(orderPeriods);
    }
}
