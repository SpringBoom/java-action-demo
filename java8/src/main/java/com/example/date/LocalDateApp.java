package com.example.date;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author SpringBoom
 * @date 2024/7/8
 */
public class LocalDateApp {

  public static void main(String[] args) {
    System.out.println(convertFromUtilDateUsingInstant(new Date()));

    // java.sql.Date 和 java.sql.Timestamp
    java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());
    System.out.println(sqlDate.toLocalDate());

    Timestamp sqlTimestamp = new Timestamp(System.currentTimeMillis());
    System.out.println(sqlTimestamp.toLocalDateTime());
  }

  /**
   * 通过 Instant 实现 date to LocalDate 的转换
   * @param date
   * @return
   */
  public static LocalDate convertFromUtilDateUsingInstant(Date date) {
    return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
  }

}
