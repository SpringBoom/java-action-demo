package com.example.mthread.stop;

/**
 * 使用 volatile 设置标记位来停止线程，这种情景是没什么问题的
 *
 * @author shing.shi
 * @since 2025/8/18
 */
public class StopByVolatile implements Runnable {

  private volatile boolean canceled = false;

  public static void main(String[] args) throws InterruptedException {
    StopByVolatile stopByVolatile = new StopByVolatile();
    Thread thread = new Thread(stopByVolatile);
    thread.start();
    System.out.println("main sleep...");
    Thread.sleep(5000);
    System.out.println("set canceled=true");
    stopByVolatile.canceled = true;
  }

  @Override
  public void run() {
    int num = 0;
    try {
      while (!canceled && num <= 100000) {
        if (num++ % 100 == 0) {
          System.out.println("是100的倍数 num=" + num);
          Thread.sleep(1000);
        }
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
