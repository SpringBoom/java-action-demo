package com.example.mthread.create;

/**
 * 比较 Thread  start 和 run 方法
 *
 * @author shing.shi
 * @since 2025/8/17
 */
public class StartAndRun {

  public static void main(String[] args) {
    Runnable runnable = () -> {
      System.out.println(Thread.currentThread().getName() + " runnable run...");
    };
    // main
    runnable.run();
    // Thread-0
    Thread thread = new Thread(runnable);
    thread.run();
    thread.start();
  }

}
