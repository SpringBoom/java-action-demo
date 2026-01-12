package com.example.mthread.states;

/**
 * 线程状态（共六种），三种常见：NEW -> RUNNABLE -> TERMINATED
 * @author shing.shi
 * @since 2025/9/5
 */
public class NewRunnableTerminated {
  public static void main(String[] args) throws InterruptedException {
    Thread thread = new Thread(() -> {
      System.out.println("hello world");
    });
    System.out.println(thread.getState());
    thread.start();
    System.out.println(thread.getState());
    System.out.println(thread.getState());
    Thread.sleep(10);
    System.out.println(thread.getState());
  }
}
