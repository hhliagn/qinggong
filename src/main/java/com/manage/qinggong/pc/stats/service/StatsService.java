package com.manage.qinggong.pc.stats.service;

import com.manage.qinggong.app.order.mapper.OrderMapper;
import com.manage.qinggong.app.order.pojo.OrderExample;
import com.manage.qinggong.base.utils.DateUtils;
import com.manage.qinggong.pc.stats.pojo.Month;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class StatsService {

    @Autowired
    private OrderMapper orderMapper;

    public Long dayStats(Integer type, Date date) {
        List<Date> dates = DateUtils.beginAndEndOfDaySpec(date);
        return getCount(type,dates);
    }

    public List<Month> monthStats(Integer type, Date date) throws ParseException {
        List<Long> monthCounts = new ArrayList<>();
//        int year = date.getYear();
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        int year = cale.get(Calendar.YEAR);
        List<String> dateStrs = new ArrayList<>();
        for (int i = 1; i < 13; i++) {
            String dateStr = null;
            if (i < 10) dateStr = year + "-0" + i + "-01 00:00:00";
            else dateStr = year + "-" + i + "-01 00:00:00";
            dateStrs.add(dateStr);
        }
        for (String dateStr : dateStrs) {
            Date date1 = DateUtils.strToDate(dateStr, "yyyy-MM-dd HH:mm:ss");
            List<Date> dates = DateUtils.beginAndEndOfMonthSpec(date1);
            Long count = getCount(type, dates);
            monthCounts.add(count);
        }
        List<Month> months = new ArrayList<>();
        for (int i = 1; i < monthCounts.size() + 1; i++) {
            Month month = new Month();
            month.setMonth(i + "月");
            month.setNum(monthCounts.get(i - 1));
            months.add(month);
        }
        return months;
    }

    public Long yearStats(Integer type,Date year) {
        List<Date> dates = DateUtils.beginAndEndOfYearSpec(year);
        return getCount(type, dates);
    }

    private Long getCount(Integer type, List<Date> dates){
        OrderExample example = new OrderExample();
        OrderExample.Criteria criteria = example.createCriteria();
        Date begin = dates.get(0);
        Date end = dates.get(1);
        criteria.andOrderDateBetween(begin, end);
//        criteria.andStatusEqualTo(0);
        if (type == 0) criteria.andOrderTypeEqualTo(0);
        else criteria.andOrderTypeEqualTo(1);
        return orderMapper.sumByExample(example);
    }

    public static void main(String[] args) throws ParseException {
        String dateStr = "2019-01-01 00:00:00";
        Date date = DateUtils.strToDate(dateStr, "yyyy-MM-dd HH:mm:ss");
        List<Date> dates = DateUtils.beginAndEndOfYearSpec(date);
        System.out.println(dates);
    }
}
