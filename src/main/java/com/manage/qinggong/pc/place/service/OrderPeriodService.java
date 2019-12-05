package com.manage.qinggong.pc.place.service;

import com.manage.qinggong.pc.place.mapper.OrderPeriodMapper;
import com.manage.qinggong.pc.place.pojo.OrderPeriod;
import com.manage.qinggong.pc.place.pojo.OrderPeriodExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderPeriodService {
    @Autowired
    private OrderPeriodMapper orderPeriodMapper;
    public List<OrderPeriod> all() {
        List<OrderPeriod> orderPeriods = orderPeriodMapper.selectByExample(new OrderPeriodExample());
        return orderPeriods;
    }

    public boolean add(OrderPeriod orderPeriod) {
        int i = orderPeriodMapper.insert(orderPeriod);
        return i > 0;
    }
}
