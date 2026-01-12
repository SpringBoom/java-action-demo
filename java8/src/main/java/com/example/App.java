package com.example;

import java.time.chrono.Chronology;
import java.time.temporal.TemporalQueries;
import java.time.temporal.TemporalQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 */
public class App {
  static final TemporalQuery<Chronology> CHRONO = (temporal) -> temporal.query(TemporalQueries.chronology());
  TemporalQuery<Chronology> CHRONO2 = (temporal) -> temporal.query(this.CHRONO2);

  public static void main(String[] args) {
    System.out.println("Hello World!");
    List<String> list = new ArrayList<>();

    int w = 1;
    System.out.println(~w);

    System.out.println(Integer.toBinaryString(~w));
    System.out.println(Integer.MIN_VALUE);
    System.out.println(Integer.toBinaryString(Integer.MIN_VALUE));
    System.out.println(1<<15);
  }
}
