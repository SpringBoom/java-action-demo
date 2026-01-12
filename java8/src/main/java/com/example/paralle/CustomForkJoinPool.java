package com.example.paralle;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

/**
 * @author SpringBoom
 * @date 2024/7/12
 */
public class CustomForkJoinPool {

  public static void main(String[] args) {
    ForkJoinPool pool = new ForkJoinPool(15);
    ForkJoinTask<Long> task = pool.submit(
        () -> LongStream.rangeClosed(1, 3_000_000)
            .parallel()
            .sum());
    try {
      int poolSize = pool.getPoolSize();
      System.out.println("Pool size: " + poolSize);

      long total = task.get();
      System.out.println(total);

      poolSize = pool.getPoolSize();
      System.out.println("Pool size: " + poolSize);

    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    } finally {
      pool.shutdown();
    }
  }
}
