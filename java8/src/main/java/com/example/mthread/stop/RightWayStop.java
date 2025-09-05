package com.example.mthread.stop;

/**
 * run 方法内没有 sleep 和 wait 时，如何停止线程
 *
 * @author shing.shi
 * @since 2025/8/18
 */
public class RightWayStop implements Runnable {

  @Override
  public void run() {
    int num = 0;
    // 1073740000
    // 387430000
    // while (num <= Integer.MAX_VALUE / 2) {
    while (!Thread.interrupted() && num <= Integer.MAX_VALUE / 2) {
      if (num % 10000 == 0) {
        System.out.println("num = " + num);
      }
      num++;
    }
    System.out.println("run finished");
  }

  public static void main(String[] args) throws InterruptedException {
    RightWayStop rightWayStop = new RightWayStop();
    Thread thread = new Thread(rightWayStop);
    thread.start();
    Thread.sleep(500);
    thread.interrupt();
  }
}
