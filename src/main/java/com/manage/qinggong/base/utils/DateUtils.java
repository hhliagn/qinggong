package com.manage.qinggong.base.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtils {
    private static Calendar cale = Calendar.getInstance();
    public static String dateToStr(Date date, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static Date strToDate(String dateStr, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(dateStr);
    }

    public static Integer dayOfWeek(Date date){
        cale.setTime(date);
        int i = cale.get(Calendar.DAY_OF_WEEK);
        if (i == 1) return 7;
        return i - 1;
    }

    public static List<Date> beginAndEndOfDay(){
        List<Date> list = new ArrayList<>();
        long current=System.currentTimeMillis();    //当前时间毫秒数
        long zeroT=current/(1000*3600*24)*(1000*3600*24)- TimeZone.getDefault().getRawOffset();  //今天零点零分零秒的毫秒数
//        String zero = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(zeroT);
        Date begin = new Date(zeroT);
        long endT=zeroT+24*60*60*1000-1;  //今天23点59分59秒的毫秒数
//        String end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endT);
        Date end = new Date(endT);
        list.add(begin);
        list.add(end);
        return list;
    }

    public static List<Date> beginAndEndOfMonth(){
        List<Date> list = new ArrayList<>();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        Date begin = cale.getTime();

        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        Date end = cale.getTime();
        list.add(begin);
        list.add(end);
        return list;
    }

    //某一天的开始和结束
    public static List<Date> beginAndEndOfDaySpec(Date date){
        List<Date> list = new ArrayList<>();
        cale.setTime(date);
        cale.set(Calendar.HOUR_OF_DAY, 0);
        cale.set(Calendar.MINUTE, 0);
        cale.set(Calendar.SECOND, 0);
        cale.set(Calendar.MILLISECOND, 0);
        Date begin = cale.getTime();
        cale.set(Calendar.HOUR_OF_DAY, 23);
        cale.set(Calendar.MINUTE, 59);
        cale.set(Calendar.SECOND, 59);
        cale.set(Calendar.MILLISECOND, 999);
        Date end = cale.getTime();
        list.add(begin);
        list.add(end);
        return list;
    }

    //某一年某一月的开始和结束
    public static List<Date> beginAndEndOfMonthSpec(Date date){
        List<Date> list = new ArrayList<>();
        cale.setTime(date);
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        Date begin = cale.getTime();

        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        cale.set(Calendar.HOUR_OF_DAY, 23);
        cale.set(Calendar.MINUTE, 59);
        cale.set(Calendar.SECOND, 59);
        cale.set(Calendar.MILLISECOND, 999);
        Date end = cale.getTime();
        list.add(begin);
        list.add(end);
        return list;
    }

    //某一年的开始和结束
    public static List<Date> beginAndEndOfYearSpec(Date year){
        List<Date> list = new ArrayList<>();
        cale.setTime(year);
        cale.set(Calendar.DAY_OF_YEAR, 1);
        Date begin = cale.getTime();
        cale.add(Calendar.YEAR, 1);
        cale.set(Calendar.DAY_OF_YEAR, -1);
        cale.set(Calendar.HOUR_OF_DAY, 23);
        cale.set(Calendar.MINUTE, 59);
        cale.set(Calendar.SECOND, 59);
        cale.set(Calendar.MILLISECOND, 999);
        Date end = cale.getTime();
        list.add(begin);
        list.add(end);
        return list;
    }
}
