package com.example.mthread.stop;

/**
 * 最佳实践：优先在方法签名中抛出异常，在线程 run 方法中处理
 *
 * @author shing.shi
 * @since 2025/8/18
 */
public class StopThreadInProd {
  public static void main(String[] args) throws InterruptedException {
    Thread thread = new Thread(() -> {
      while (!Thread.interrupted()) {
        System.out.println("run");
        try {
          something();
        } catch (InterruptedException e) {
          System.out.println("检测到中断信号，停止线程");
          e.printStackTrace();
          break;
        }
      }
      System.out.println("线程运行结束");
    });

    thread.start();
    Thread.sleep(1000);
    thread.interrupt();
    System.out.println("main finished");
  }

  public static void something() throws InterruptedException {
    Thread.sleep(2000);
  }
}
