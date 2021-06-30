package com.zjc.java8;


import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.Period;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

/**
 * @ClassName : TimeTest
 * @Author : zhangjiacheng
 * @Date : 2020/12/3
 * @Description : TODO
 */
public class Java8TimeTest {

    public static void main(String[] args) {
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);
        System.out.println(localDate.getYear() + " " + localDate.getMonthValue() + " " + localDate.getDayOfMonth());
        LocalDate of = LocalDate.of(2012, 3, 25);
        System.out.println(of);
        MonthDay monthDay = MonthDay.of(of.getMonth(),of.getDayOfMonth());
        MonthDay from = MonthDay.from(LocalDate.of(2011, 3, 25));
        if (monthDay.equals(from)){
            System.out.println(111);
        }


        LocalTime now = LocalTime.now();
        System.out.println(now);
        LocalTime localTime = now.plusHours(2).plusMinutes(22);
        System.out.println(localTime);

        LocalDate plus = localDate.plus(2, ChronoUnit.WEEKS);
        System.out.println(plus);
        LocalDate minus = localDate.minus(2, ChronoUnit.MONTHS);
        System.out.println(minus);

        Clock clock = Clock.systemDefaultZone();
        System.out.println(clock);

        LocalDate now1 = LocalDate.now();
        LocalDate of1 = LocalDate.of(2020, 12, 23);
        System.out.println(now1.isBefore(of1));
        System.out.println(now1.isAfter(of1));
        System.out.println(now1.isEqual(of1));

//        Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();
//        availableZoneIds.forEach(System.out::println);
//
//        TreeSet<Comparable> comparables = Sets.newTreeSet(availableZoneIds);
//        comparables.forEach(System.out::println);

        ZoneId of2 = ZoneId.of("Asia/Shanghai");
        LocalDateTime now2 = LocalDateTime.now();
        System.out.println(now2);
        ZonedDateTime of3 = ZonedDateTime.of(now2, of2);
        System.out.println(of3);

        YearMonth now3 = YearMonth.now();
        System.out.println(now3);
        System.out.println(now3.lengthOfMonth());
        System.out.println(now3.isLeapYear());

        YearMonth of4 = YearMonth.of(2019, 2);
        String s = of4.minus(Period.ofMonths(1)).toString();
        System.out.println(of4.lengthOfMonth());
        System.out.println(of4.lengthOfYear());
        System.out.println(of4.isLeapYear());

        LocalDate locc1 = LocalDate.now();
        LocalDate locc2 = LocalDate.of(2021,12,2);
        Period between = Period.between(locc1, locc2);
        System.out.println(between.getYears());
        System.out.println(between.getMonths());
        System.out.println(between.getDays());

        Instant now4 = Instant.now();
        System.out.println(now4);
        ZonedDateTime zonedDateTime = now4.atZone(of2);
        System.out.println(zonedDateTime);
    }
}
