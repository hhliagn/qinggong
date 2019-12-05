package com.manage.qinggong.pc.stats.controller;

import com.manage.qinggong.base.Response;
import com.manage.qinggong.pc.stats.service.StatsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stats")
public class StatsController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StatsService statsService;

    @PostMapping(value = "")
    public Response dayStats(@RequestParam("type") int type){
        if (type == 0 || type > 2)  return new Response("参数错误");
        try {
            Long count = 0L;
            if (type == 0) count = statsService.dayStats();
            else if (type == 1) count = statsService.monthStats();
            else count = statsService.yearStats();
            return new Response("统计成功", count);
        }catch (Exception e){
            return new Response("统计当日数据出错");
        }
    }
}