package com.example.paralle;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author SpringBoom
 * @date 2024/7/12
 */
public class ExecutorAndFutureDemo {

  public static void main(String[] args) {
    ExecutorService service = Executors.newCachedThreadPool();
    Future<String> future = service.submit(()->{
      Thread.sleep(100);
      return  "Hello, World!";
    });

    future.cancel(true);
    System.out.println("Processing...");
    resultIfNotCancelled(future);
  }

  public static void resultIfNotCancelled(Future<String> future) {
    try {
      if (!future.isCancelled()) {
        // get 方法用于在Future 中检索值，该方法属于阻塞调用（blocking call）
        System.out.println(future.get());
      } else {
        System.out.println("Cancelled");
      }
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
  }
}
