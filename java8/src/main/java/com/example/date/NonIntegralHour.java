package com.example.date;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author SpringBoom
 * @date 2024/7/12
 */
public class NonIntegralHour {
    public static void main(String[] args) {
      Instant instant = Instant.now();
      ZonedDateTime current = instant.atZone(ZoneId.systemDefault());
      System.out.printf("Current time is %s%n%n", current);

      System.out.printf("%10s %20s %13s%n", "Offset", "ZoneId", "Time");
      ZoneId.getAvailableZoneIds().stream()
        .map(ZoneId::of)
        .filter(zoneId -> {
          ZoneOffset offset = instant.atZone(zoneId).getOffset();
          return  offset.getTotalSeconds() % (60 * 60) != 0;
        })
        .sorted(Comparator.comparingInt(zoneId ->
            instant.atZone(zoneId).getOffset().getTotalSeconds()))
        .forEach(zoneId -> {
          ZonedDateTime zdt = current.withZoneSameInstant(zoneId);
          System.out.printf("%10s %25s %10s%n",
              zdt.getOffset(), zoneId,
              zdt.format(DateTimeFormatter.ofLocalizedTime(
                  FormatStyle.SHORT)));
        });

      List<String> regionNamesForOffset = getRegionNamesForOffset(ZoneOffset.UTC);
      regionNamesForOffset.stream().forEach(System.out::println);

      between();
      until();

      Instant start = Instant.now();
      // 调用需要计时的方法
      Instant end = Instant.now();
      System.out.println(getTiming(start, end) + " seconds");
    }

  public static double getTiming(Instant start, Instant end) {
    return Duration.between(start, end).toMillis() / 1000.0;
  }

  /**
   * 计算两个时间之间的时间
   */
  public static void between(){
      LocalDate electionDay = LocalDate.of(2020, Month.NOVEMBER, 3);
      LocalDate today = LocalDate.now();

      System.out.printf("%d day(s) to go...%n",
          ChronoUnit.DAYS.between(today, electionDay));
  }

  public static void until(){
    LocalDate electionDay = LocalDate.of(2020, Month.NOVEMBER, 3);
    LocalDate today = LocalDate.now();

    Period until = today.until(electionDay);
    int years = until.getYears();
    int months = until.getMonths();
    int days = until.getDays();
    System.out.printf("%d year(s), %d month(s), and %d day(s)%n", years, months, days);
  }

  /**
   * 根据给定的偏移量，获取相应的地区名
   * @param offset
   * @return
   */
  public static List<String> getRegionNamesForOffset(ZoneOffset offset) {
    ZoneId zoneId1 = ZoneId.of(offset.getId());
    String string = zoneId1.toString();
    System.out.println(string);

    LocalDateTime now = LocalDateTime.now();
    return ZoneId.getAvailableZoneIds().stream()
            .map(ZoneId::of)
            .filter(zoneId -> now.atZone(zoneId).getOffset().equals(offset))
            .map(ZoneId::toString)
            .sorted()
            .collect(Collectors.toList());
  }
}
