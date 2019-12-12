package com.manage.qinggong.app.order.service;

import com.manage.qinggong.app.order.mapper.OrderMapper;
import com.manage.qinggong.app.order.pojo.Order;
import com.manage.qinggong.app.order.pojo.OrderExample;
import com.manage.qinggong.base.pojo.ErrorCode;
import com.manage.qinggong.base.pojo.Response;
import com.manage.qinggong.base.utils.DateUtils;
import com.manage.qinggong.base.utils.RedisService;
import com.manage.qinggong.pc.place.mapper.OrderPeriodMapper;
import com.manage.qinggong.pc.place.mapper.PlaceSetupMapper;
import com.manage.qinggong.pc.place.pojo.OrderPeriod;
import com.manage.qinggong.pc.place.pojo.OrderPeriodExample;
import com.manage.qinggong.pc.place.pojo.PlaceSetup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrderService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderPeriodMapper orderPeriodMapper;
    @Autowired
    private PlaceSetupMapper placeSetupMapper;
    @Autowired
    private RedisService redisService;
    public Response submitOrder(Order order) {
        //加判断，是否该时间可以预约 并且 该时间段人数未满
        String orderUserName = order.getOrderUserName();
        Date orderDate = order.getOrderDate();
        String orderTime = order.getOrderTime();
        String date = DateUtils.dateToStr(orderDate, "yyyy-MM-dd");
        Integer dayOfWeek = DateUtils.dayOfWeek(orderDate);

        //查询开馆时间 place_setup open_time
        PlaceSetup placeSetup = placeSetupMapper.selectByPrimaryKey(1);
        if (placeSetup == null) return new Response("展馆未设置", ErrorCode.ERROR);
        String openTime = placeSetup.getOpenTime();
        String[] split = openTime.split("-");
        Integer openBegin = Integer.valueOf(split[0]);
        Integer openEnd = Integer.valueOf(split[1]);
        if (dayOfWeek < openBegin || dayOfWeek > openEnd) return new Response("不在开馆时间内", ErrorCode.ERROR);

        //查询人数限制 order_time limit
        OrderPeriodExample example = new OrderPeriodExample();
        OrderPeriodExample.Criteria criteria = example.createCriteria();
        criteria.andOrderTimeEqualTo(orderTime);
        List<OrderPeriod> orderPeriods = orderPeriodMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(orderPeriods)) return new Response("不在可预约的时间段内", ErrorCode.ERROR);
        OrderPeriod orderPeriod = orderPeriods.get(0);
        Long limit = orderPeriod.getOrderLimit();
        Long orderCount = order.getOrderCount();

        Object o = new Object();
        boolean flag = true;
        synchronized (o){
            //12-12 fix:提交预约的时候根据 order_date,order_time 查出所有 order,累加 order_count 和 limit 比较。
            OrderExample example1 = new OrderExample();
            OrderExample.Criteria criteria1 = example1.createCriteria();
            List<Date> dates = DateUtils.beginAndEndOfDaySpec(orderDate);
            Date begin = dates.get(0);
            Date end = dates.get(1);
            criteria1.andOrderDateBetween(begin,end);
            criteria1.andOrderTimeEqualTo(orderTime);
            long hasOrderCount = 0L;
            List<Order> orders = orderMapper.selectByExample(example1);
            for (Order order1 : orders) {
                Long orderCount1 = order1.getOrderCount();
                hasOrderCount += orderCount1;
            }
            if (limit - hasOrderCount < orderCount) return new Response("预约人数已满", ErrorCode.ERROR);

            //查询数据库是否已经有这条预约，如果有不能重复插入
            OrderExample example2 = new OrderExample();
            OrderExample.Criteria criteria2 = example2.createCriteria();
            criteria2.andOrderDateEqualTo(orderDate);
            criteria2.andOrderTimeEqualTo(orderTime);
            criteria2.andOrderUserNameEqualTo(orderUserName);
            criteria2.andOrderCountEqualTo(orderCount);
            long records = orderMapper.countByExample(example2);
            if (records > 0) return new Response("已存在该预约", ErrorCode.ERROR);

            //插入记录
            if (flag) {
                order.setStatus(0);
                order.setCreateTime(new Date());
                int i = orderMapper.insertAndGet(order);
                if (i < 0) return new Response("提交预约失败", ErrorCode.ERROR);
            }
        }
        return new Response("提交预约成功", ErrorCode.SUCCESS, order.getOrderId());
    }

//    public boolean verify(Order order) {
//        Integer orderId = order.getOrderId();
//        if (orderId == null || orderId <= 0) return false;
//        String userName = order.getOrderUserName();
//        String orderDateStr = order.getOrderDateStr();
//        String orderTime = order.getOrderTime();
//        Long orderCount = order.getOrderCount();
//        Date orderDate = null;
//        try {
//            orderDate = DateUtils.strToDate(orderDateStr, "yyyy-MM-dd");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        OrderExample example = new OrderExample();
//        OrderExample.Criteria c = example.createCriteria();
//        c.andOrderUserNameEqualTo(userName);
//        List<Date> dates = DateUtils.beginAndEndOfDaySpec(orderDate);
//        Date begin = dates.get(0);
//        Date end = dates.get(1);
//        c.andOrderDateBetween(begin, end);
//        c.andOrderTimeEqualTo(orderTime);
//        c.andOrderCountEqualTo(orderCount);
//
//        Object o = new Object();
//        synchronized (o){
//            //查询可以有多少人通过
//            List<Order> orders = orderMapper.selectByExample(example);
//            if (CollectionUtils.isEmpty(orders)) return false;
//            Order order1 = orders.get(0);
//            Long orderCount1 = order1.getOrderCount();
//            Long passed = order1.getPassed();
//            if (passed >= orderCount1) {
//                order1.setStatus(1);
//                orderMapper.updateByPrimaryKeySelective(order1);
//                return false;
//            }
//            passed += 1;
//            order1.setPassed(passed);
//            orderMapper.updateByPrimaryKeySelective(order1);
//            if (passed >= orderCount){
//                order1.setStatus(1);
//                orderMapper.updateByPrimaryKeySelective(order1);
//            }
//            return true;
//        }
//    }

    public boolean verify(Order order) {
        Integer orderId = order.getOrderId();
        Long orderCount = order.getOrderCount();
        if (orderId == null || orderId <= 0) return false;
        Object o = new Object();
        synchronized (o){
            //查询可以有多少人通过
            Long passed = null;
            String key = "order_" + orderId;
            passed = (Long) redisService.get(key);
            if (passed == null) passed = 0L;
            if (passed >= orderCount) {
                Order order2 = orderMapper.selectByPrimaryKey(orderId);
                if (order2 == null) return false;
                order2.setStatus(1);
                order2.setPassed(orderCount);
                orderMapper.updateByPrimaryKeySelective(order2);
                return false;
            }
            passed ++;
            redisService.set(key, passed, 5*60);
            return true;
        }
    }
}
