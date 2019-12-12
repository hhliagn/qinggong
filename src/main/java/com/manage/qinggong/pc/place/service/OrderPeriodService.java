package com.manage.qinggong.pc.place.service;

import com.manage.qinggong.pc.place.mapper.OrderPeriodMapper;
import com.manage.qinggong.pc.place.pojo.OrderPeriod;
import com.manage.qinggong.pc.place.pojo.OrderPeriodExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrderPeriodService {
    @Autowired
    private OrderPeriodMapper orderPeriodMapper;
    public List<OrderPeriod> all() {
        List<OrderPeriod> orderPeriods = orderPeriodMapper.selectByExample(new OrderPeriodExample());
        return orderPeriods;
    }

    public boolean add(OrderPeriod orderPeriod) {
        orderPeriod.setCreateTime(new Date());
        orderPeriod.setStatus(0);
        int i = orderPeriodMapper.insertSelective(orderPeriod);
        return i > 0;
    }
}
