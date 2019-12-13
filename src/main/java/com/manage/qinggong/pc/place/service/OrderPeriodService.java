package com.manage.qinggong.pc.place.service;

import com.manage.qinggong.base.pojo.ErrorCode;
import com.manage.qinggong.base.pojo.Response;
import com.manage.qinggong.pc.place.mapper.OrderPeriodMapper;
import com.manage.qinggong.pc.place.mapper.PlaceSetupMapper;
import com.manage.qinggong.pc.place.pojo.OrderPeriod;
import com.manage.qinggong.pc.place.pojo.OrderPeriodExample;
import com.manage.qinggong.pc.place.pojo.PlaceSetup;
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
    @Autowired
    private PlaceSetupMapper placeSetupMapper;
    public List<OrderPeriod> all() {
        List<OrderPeriod> orderPeriods = orderPeriodMapper.selectByExample(new OrderPeriodExample());
        return orderPeriods;
    }

    public Response submit(List<OrderPeriod> orderPeriods) {
        //12-13 fix:提交之前要判断时间是否在展馆设置的运营时间内
        //查出展馆运营时间
        PlaceSetup placeSetup = placeSetupMapper.selectByPrimaryKey(1);
        if (placeSetup == null) return new Response("展馆时间未设置", ErrorCode.ERROR);
        String operateTime = placeSetup.getOperateTime();
        String[] placeSetUpArray = operateTime.split("-");
        String pStart = placeSetUpArray[0];
        String pEnd = placeSetUpArray[1];
        Integer pStartInt = Integer.valueOf(pStart.substring(0, 2));
        Integer pEndInt = Integer.valueOf(pEnd.substring(0, 2));
        boolean flag = true;
        for (OrderPeriod orderPeriod : orderPeriods) {
            Long limit = orderPeriod.getOrderLimit();
            if (limit < 0) return new Response("人数限制不能小于 0", ErrorCode.ERROR);
            String orderTime = orderPeriod.getOrderTime();
            String[] orderPeriodArray = orderTime.split("-");
            String oStart = orderPeriodArray[0];
            String oEnd = orderPeriodArray[1];
            Integer oStartInt = Integer.valueOf(oStart.substring(0, 2));
            Integer oEndInt = Integer.valueOf(oEnd.substring(0, 2));
            if (oStartInt >= oEndInt
                    || oStartInt < pStartInt
                    || oStartInt > pEndInt
                    || oEndInt < pStartInt
                    || oEndInt > pEndInt) flag = false;
        }
        if (!flag) return new Response("提交的记录中和展馆运营时间有冲突", ErrorCode.ERROR);
        //提交的时候数据库记录先全部删除，再全部插入
        int i = orderPeriodMapper.deleteByExample(new OrderPeriodExample());
        if (i <= 0) new Response("删除全部记录失败", ErrorCode.ERROR);
        boolean flag2 = true;
        for (OrderPeriod orderPeriod : orderPeriods) {
            orderPeriod.setCreateTime(new Date());
            orderPeriod.setStatus(0);
            int i1 = orderPeriodMapper.insertSelective(orderPeriod);
            if (i1 <= 0) flag2 = false;
        }
        if (flag2) return new Response("添加成功", ErrorCode.SUCCESS);
        return new Response("添加失败", ErrorCode.ERROR);
    }
}
