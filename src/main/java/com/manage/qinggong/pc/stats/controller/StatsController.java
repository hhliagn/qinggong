package com.manage.qinggong.pc.stats.controller;

import com.manage.qinggong.base.pojo.ErrorCode;
import com.manage.qinggong.base.pojo.Response;
import com.manage.qinggong.pc.stats.pojo.Month;
import com.manage.qinggong.pc.stats.service.StatsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stats")
public class StatsController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StatsService statsService;

    @GetMapping(value = "")
    public Response dayStats(@RequestParam("date") Date date, @RequestParam("year") Date year, @RequestParam("type") int type){
        //type: 1-个人  2-团队
        if (type < 0 || type > 1)  return new Response("参数错误", ErrorCode.ERROR);
        Map<String, Object> data = new HashMap<>();
        try {
            Long dayCount = statsService.dayStats(type, date);
            List<Month> monthCounts = statsService.monthStats(type, year);
            Long yearCount = statsService.yearStats(type, year);
            data.put("dayCount", dayCount);
            data.put("monthCounts", monthCounts);
            data.put("yearCount", yearCount);
            return new Response("统计成功", ErrorCode.SUCCESS, data);
        }catch (Exception e){
            return new Response("统计当日数据出错", ErrorCode.ERROR);
        }
    }
}