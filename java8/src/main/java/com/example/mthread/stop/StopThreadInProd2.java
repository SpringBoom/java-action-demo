package com.example.mthread.stop;

/**
 * 最佳实践2：catch InterruptedException 后，恢复中断状态
 *
 * @author shing.shi
 * @since 2025/8/18
 */
public class StopThreadInProd2 {
  public static void main(String[] args) throws InterruptedException {
    Thread thread = new Thread(() -> {
      while (!Thread.interrupted()) {
        System.out.println("run");
        something();
      }
      System.out.println("线程运行结束");
    });

    thread.start();
    Thread.sleep(1000);
    thread.interrupt();
    System.out.println("main finished");
  }

  public static void something() {
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      System.out.println("something 方法中捕获到中断信号");
      e.printStackTrace();
      // Thread.currentThread().interrupt();
    }
  }
}
