package com.example.mthread.create;

import org.apache.logging.log4j.core.util.ExecutorServices;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author shing.shi
 * @since 2025/8/17
 */
public class ThreadAndRunnable {
  public static void main(String[] args) {
    /**
     * Thread 同时 Runnable、run
     */
    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        System.out.println("Runnable run...");
      }
    }) {
      @Override
      public void run() {
        System.out.println("Thread run...");
      }
    };
    thread.start();

    ExecutorService executorService = Executors.newCachedThreadPool();
    Future<?> submit = executorService.submit(() -> {
      System.out.println("executorService.submit...");
    });

    Future<?> submit2 = executorService.submit(() -> {
      System.out.println("executorService.submit...");
    }, "OK");
    try {
      Object o = submit.get();
      System.out.println(o);
      Object o1 = submit2.get();
      System.out.println(o1);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
