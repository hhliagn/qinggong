package com.manage.qinggong.pc.stats.service;

import com.manage.qinggong.app.order.mapper.OrderMapper;
import com.manage.qinggong.app.order.pojo.OrderExample;
import com.manage.qinggong.base.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class StatsService {

    @Autowired
    private OrderMapper orderMapper;

    public Long dayStats() {
        List<Date> dates = DateUtils.beginAndEndOfDay();
        return getCount(dates);
    }

    public Long monthStats() {
        List<Date> dates = DateUtils.beginAndEndOfMonth();
        return getCount(dates);
    }

    public Long yearStats() {
        List<Date> dates = DateUtils.beginAndEndOfYear();
        return getCount(dates);
    }

    private Long getCount(List<Date> dates){
        OrderExample example = new OrderExample();
        OrderExample.Criteria criteria = example.createCriteria();
        Date begin = dates.get(0);
        Date end = dates.get(1);
        criteria.andOrderDateBetween(begin, end);
        criteria.andStatusEqualTo(0);
        return orderMapper.countByExample(example);
    }

    public static void main(String[] args) {
        List<Date> dates = DateUtils.beginAndEndOfYear();
        System.out.println(dates);
    }
}
