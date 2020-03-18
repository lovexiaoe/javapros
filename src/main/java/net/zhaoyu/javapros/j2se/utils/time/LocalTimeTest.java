package net.zhaoyu.javapros.j2se.utils.time;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Java8提供了新的时间接口,LocalDate,LocalTime,LocalDateTime。
 * 计算日期用LocalDate,计算日期加时刻用LocalDateTime，时刻用LocalTime。
 * 相对于Date和Calendar，学习成本更低，操作十分方便。
 * 并且LocalDate系列是线程安全的。相对于Date来说，Date和SimpleDateFormat都是线程不安全的。
 */
public class LocalTimeTest {


    static void createLocalTime(){
        //创建now
        LocalDateTime dateTime=LocalDateTime.now();
        System.out.println("now is "+dateTime);

        //formatter解析
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse("2019-11-27", formatter);
        System.out.println("create from str 2019-11-27"+localDate);

        //直接从字符串解析
        localDate = LocalDate.parse("2019-11-26");
        LocalTime localTime= LocalTime.parse("10:24:11");

        //of方法创建
        localDate=LocalDate.of(2019,11,27);
        System.out.println("create from LocalDate.of " + localDate);

    }


    static void dateTimeConvert(){
        //LocalDateTime，LocalDate,LocalTime的互转。
        LocalDate date = LocalDate.of(2018, 10, 31);
        LocalTime time = LocalTime.of(13, 45, 20);
        LocalDateTime dt3 = date.atTime(13, 45, 20);
        LocalDateTime dt4 = date.atTime(time);
        LocalDateTime dt5 = time.atDate(date);

        //设置特定的字段
        LocalDate date1 = LocalDate.of(2018, 10, 31);//2018-10-31
        LocalDate date2 = date1.withYear(2011);//2011-10-31
        LocalDate date3 = date2.withDayOfMonth(25);//2011-10-25
        LocalDate date4 = date3.with(ChronoField.MONTH_OF_YEAR, 9);//2011-09-25

        //时间加减
        LocalDate date5 = date1.plusWeeks(1);//2018-11-07
        LocalDate date6 = date5.minusYears(3);//2015-11-07
        LocalDate date7 = date6.plus(6, ChronoUnit.MONTHS);//2016-05-07
    }

    static void dateConvert(){
        //Date 转 LocalDateTime
        Date date = new Date();
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);

        //LocalDateTime 转Date
        instant = localDateTime.atZone(zone).toInstant();
        date = Date.from(instant);

        //LocalDate 转 Date
        LocalDate localDate = LocalDate.now();
        instant = localDate.atStartOfDay().atZone(zone).toInstant();
        date = Date.from(instant);
    }

}
