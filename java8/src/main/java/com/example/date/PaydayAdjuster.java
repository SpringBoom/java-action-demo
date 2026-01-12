package com.example.date;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.stream.IntStream;

/**
 * Java 官方教程以PaydayAdjuster 类为例演示了自定义调节器的应用：
 * 假设员工在一个月中领取两次工资，且发薪日是每月 15 日和最后一天；如果某个发薪日为周末，则提前到周五
 *
 * @author SpringBoom
 * @date 2024/7/8
 */
public class PaydayAdjuster implements TemporalAdjuster {
  public Temporal adjustInto(Temporal input) {
    LocalDate date = LocalDate.from(input);
    int day;

    if (date.getDayOfMonth() < 15) {
      day = 15;
    } else {
      day = date.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
    }

    date = date.withDayOfMonth(day);
    if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
      date = date.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY));
    }

    return input.with(date);
  }

  public static void main(String[] args) {

    TemporalAdjuster adjuster = new PaydayAdjuster();
    IntStream.rangeClosed(1, 14).mapToObj(day -> LocalDate.of(2017, Month.JULY, day))
        .forEach(date -> System.out.println(date.with(adjuster).getDayOfMonth() == 14));

    IntStream.rangeClosed(15, 31).mapToObj(day -> LocalDate.of(2017, Month.JULY, day))
        .forEach(date -> System.out.println(date.with(adjuster).getDayOfMonth() == 31));
  }

}