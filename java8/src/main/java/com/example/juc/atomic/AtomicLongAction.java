package com.example.juc.atomic;

import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @since 2024/11/7
 */
public class AtomicLongAction {

  private static final Logger log = LoggerFactory.getLogger(AtomicLongAction.class);

  public static void main(String[] args) {
    AtomicLong atomicLong = new AtomicLong();
    LongStream.range(0,100).parallel().forEach(r->{
          long l = atomicLong.incrementAndGet();
          log.info("parallel-{} get: {} ",r,l);
    });
    log.info("{}", atomicLong.get());
  }

}
