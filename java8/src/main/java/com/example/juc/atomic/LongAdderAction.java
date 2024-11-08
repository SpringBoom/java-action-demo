package com.example.juc.atomic;

import java.util.concurrent.atomic.LongAdder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @since 2024/11/7
 */
public class LongAdderAction {

  private static final Logger log = LoggerFactory.getLogger(LongAdderAction.class);

  public static void main(String[] args) {
    LongAdder adder = new LongAdder();
    adder.add(1);
    adder.increment();
    adder.decrement();
    adder.longValue();
    log.info("{}", adder);
  }
}
