package com.example.paralle.multifuture;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author shing.shi
 * @since 2026/1/12
 */
public class GamePageLinksSupplier implements Supplier<List<String>> {
  private static final String BASE = "http://gd2.mlb.com/components/game/mlb/";
  private LocalDate startDate;
  private int days;

  public GamePageLinksSupplier(LocalDate startDate, int ays) {
    this .startDate = startDate;
    this .days = days;
  }

  public List<String> getGamePageLinks(LocalDate localDate) {
    // 使用Jsoup库解析HTML网页，并提取以"gid"开头的链接
    return new ArrayList<>();
  }

  /**
   * get 方法使用 Stream.iterate 方法对某个范围内的日期进行迭代：从给定日期开始，逐天递增直至上限
   * @return
   */
  @Override
  public List<String> get() {
    return
        Stream.iterate(startDate, d -> d.plusDays(1)).limit(days).map(this
                ::getGamePageLinks).flatMap(list -> list.isEmpty() ? Stream.empty() : list.stream())
            .collect(Collectors.toList());
  }
}
