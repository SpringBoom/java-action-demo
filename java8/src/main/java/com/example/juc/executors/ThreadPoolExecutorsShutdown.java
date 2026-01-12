package com.example.juc.executors;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @since 2024/12/16
 */
public class ThreadPoolExecutorsShutdown {
  public static void main(String[] args) {
    ExecutorService executor = Executors.newFixedThreadPool(5);

    List<Integer> collect = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());
    for (final Integer i : collect) {

      executor.execute(() -> {
        try {
          System.out.println(Thread.currentThread().getName() + " is start " + i);
          Thread.sleep(2000);
          System.out.println(Thread.currentThread().getName() + " is running " + i);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      });
    }

    System.out.println("shutting down...");
    executor.shutdown();

    try {
      if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
        List<Runnable> runnables = executor.shutdownNow();
        for (Runnable runnable : runnables) {
          System.out.println(runnable);
        }
      }
    } catch (InterruptedException e) {
      executor.shutdownNow();
    }
  }
}
